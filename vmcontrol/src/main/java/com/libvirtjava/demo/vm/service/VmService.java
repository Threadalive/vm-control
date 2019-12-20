package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.util.Const;
import com.libvirtjava.demo.vm.util.MydomainState;
import com.libvirtjava.demo.vm.domain.VmParms;
import org.libvirt.*;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description vm操作
 * @Author zhenxing.dong
 * @Date 2019/12/18 16:22
 */
@Service
public class VmService {

    /**
     * 启动虚拟机
     *
     * @param vmUuid  虚拟机id
     * @param connect 连接对象
     * @return 0:启动成功 1:启动失败
     */
    public int startVm(String vmUuid, Connect connect) {
        Domain dom;
        try {
            dom = connect.domainLookupByUUIDString(vmUuid);
            dom.create();
        } catch (LibvirtException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    /**
     * 关闭虚拟机
     *
     * @param vmUuid  虚拟机id
     * @param connect 连接对象
     * @return 0:关闭成功 1:关闭失败
     */
    public int stopVm(String vmUuid, Connect connect) {
        Domain dom;
        try {
            dom = connect.domainLookupByUUIDString(vmUuid);
            dom.destroy();
        } catch (LibvirtException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    /**
     * 创建新的虚拟机
     *
     * @param vmParms 虚拟机参数
     * @param connect 连接对象
     * @return 0:关闭成功 1:关闭失败
     */
    public int createVm(VmParms vmParms, Connect connect) {
        String volPath = createDisk(vmParms.getSp(), vmParms.getName() + "_disk", vmParms.getDiskSize(), connect);
        try {
            connect.domainDefineXML(String.format(Const.XMLDESC, vmParms.getName(), vmParms.getMem(), vmParms.getMem(), vmParms.getCpu(), volPath, vmParms.getIsopath()));
            return 0;
        } catch (LibvirtException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 创建磁盘
     *
     * @param sp      存储池
     * @param name    磁盘名
     * @param size    磁盘大小
     * @param connect 连接对象
     * @return
     */
    public String createDisk(String sp, String name, long size, Connect connect) {
        try {
            StoragePool pool = connect.storagePoolLookupByName(sp);
            StorageVol vol = pool.storageVolCreateXML(String.format(Const.VOLXML, name, size, name), 0);
            return vol.getPath();
        } catch (LibvirtException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 显示当前镜像
     *
     * @param connect 连接
     * @return Map<卷名       ，       卷路径>
     */
    public Map<String, String> listIsoVolumes(Connect connect) {
        Map<String, String> isos = new HashMap();
        try {
            for (String c : connect.listStoragePools()) {
                StoragePool po = connect.storagePoolLookupByName(c);
                for (String v : po.listVolumes()) {
                    StorageVol vol = po.storageVolLookupByName(v);
                    if (vol.getName().endsWith(".iso")) {
                        isos.put(vol.getName(), vol.getPath());
                    }
                }
            }
        } catch (LibvirtException e) {
            e.printStackTrace();
        }
        return isos;
    }

    /**
     * 显示存储池
     *
     * @param connect 连接对象
     * @return 存储池名数组
     */
    public String[] listStoragePools(Connect connect) {
        try {
            return connect.listStoragePools();
        } catch (LibvirtException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将domain信息转存至Map中
     *
     * @param domain 虚拟机实体
     * @return domain信息映射集
     * @throws LibvirtException
     */
    public Map<String, Object> convertDomainInfo(Domain domain) throws LibvirtException {
        Map<String, Object> domainMap = new HashMap<String, Object>();
        domainMap.put("uuid", domain.getUUIDString());
        domainMap.put("name", domain.getName());
        domainMap.put("cpus", domain.getInfo().nrVirtCpu);
        domainMap.put("memory", domain.getMaxMemory());
        domainMap.put("state", MydomainState.values()[domain.getInfo().state.ordinal()]);
        return domainMap;
    }

    /**
     * 获取domain列表
     *
     * @param conn 连接对象
     * @return domain
     */
    public List<Map<String, Object>> getDomainList(Connect conn) {
        List<Map<String, Object>> domains = new ArrayList();
        String[] domStates = {"nostate", "running"};

        try {
            //查询已激活domain列表
            for (String acDomName : conn.listDefinedDomains()) {
                domains.add(convertDomainInfo(conn.domainLookupByName(acDomName)));
            }
            //查询未激活domain列表
            for (int acDomId : conn.listDomains()) {
                domains.add(convertDomainInfo(conn.domainLookupByID(acDomId)));
            }
        } catch (LibvirtException e) {
            e.printStackTrace();
        }
        return domains;
    }

    /**
     * 删除虚拟机
     *
     * @param vmUuid     虚拟机id
     * @param deleteDisk 是否删除磁盘
     * @param connect    连接对象
     * @return 0:删除成功 -1:删除失败
     */
    public int deleteVm(String vmUuid, boolean deleteDisk, Connect connect) {
        Domain dom;
        try {
            dom = connect.domainLookupByUUIDString(vmUuid);
            if (deleteDisk) {
                String rawXml = dom.getXMLDesc(0);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = db.parse(new ByteArrayInputStream(rawXml.getBytes()));
                NodeList diskList = document.getElementsByTagName("disk");
                List<String> diskPaths = new ArrayList<String>();
                for (int i = 0; i < diskList.getLength(); i++) {
                    org.w3c.dom.Node node = diskList.item(i);
                    if ("disk".equals(node.getAttributes().getNamedItem("device").getTextContent())) {
                        NodeList diskChildren = node.getChildNodes();
                        for (int j = 0; j < diskChildren.getLength(); j++) {
                            org.w3c.dom.Node child = diskChildren.item(j);
                            System.out.println("---->" + child.getNodeName());
                            if (child.getNodeName().equals("source")) {
                                diskPaths.add(child.getAttributes().getNamedItem("file").getTextContent());
                            }
                        }
                    }
                }
                for (String diskPath : diskPaths) {
                    if (!diskPath.endsWith(".iso")) {
                        connect.storageVolLookupByPath(diskPath).wipe();
                        connect.storageVolLookupByPath(diskPath).delete(0);
                    }
                }
            }
            dom.undefine();
        } catch (LibvirtException e) {
            e.printStackTrace();
            return -1;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}