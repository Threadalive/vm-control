package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.domain.menu.HostRecord;
import com.libvirtjava.demo.vm.domain.menu.Node;
import com.libvirtjava.demo.vm.domain.menu.VmRecord;
import com.libvirtjava.demo.vm.mapper.HostRecordMapper;
import com.libvirtjava.demo.vm.mapper.NodeMapper;
import com.libvirtjava.demo.vm.mapper.VmRecordMapper;
import com.libvirtjava.demo.vm.util.Const;
import com.libvirtjava.demo.vm.util.MydomainState;
import com.libvirtjava.demo.vm.domain.parmsutil.VmParms;
import org.libvirt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Description vm操作
 * @Author zhenxing.dong
 * @Date 2019/12/18 16:22
 */
@Service
public class VmService {

    /**
     * 虚拟机记录操作
     */
    @Autowired
    private VmRecordMapper vmRecordMapper;

    /**
     * 结点操作
     */
    @Autowired
    private NodeMapper nodeMapper;

    /**
     * 主机记录
     */
    @Autowired
    private HostRecordMapper hostRecordMapper;

    /**
     * 日志
     */
    private Logger LOGGER = LoggerFactory.getLogger(VmService.class);

    /**
     * 启动虚拟机
     *
     * @param vmUuid  虚拟机id
     * @param connect 连接对象
     * @return 0:启动成功 1:启动失败
     */
    @Transactional(rollbackFor = Exception.class)
    public int startVm(String vmUuid, Connect connect) {
        Domain dom;
        try {
            dom = connect.domainLookupByUUIDString(vmUuid);
            dom.create();
            if (dom.isActive() == 1) {
                Node node = nodeMapper.findByVmIdAndStatus(vmUuid, Node.STATUS_ENABLED);
                HostRecord rHostRecord = hostRecordMapper.findFirstByOrderByHid();
                //更新父目录id
                nodeMapper.updateNode(rHostRecord.getHid(), node.getId());
                //更新vm状态
                vmRecordMapper.updateNode("running", vmUuid);
            }
        } catch (LibvirtException e) {
            LOGGER.error("{}", e);
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
    @Transactional(rollbackFor = Exception.class)
    public int stopVm(String vmUuid, Connect connect) {
        Domain dom;
        try {
            dom = connect.domainLookupByUUIDString(vmUuid);
            dom.destroy();
            if (dom.isActive() != 1) {
                //更新父目录id
                Node node = nodeMapper.findByVmIdAndStatus(vmUuid, Node.STATUS_ENABLED);
                nodeMapper.updateNode(node.getClusterId(), node.getId());
                vmRecordMapper.updateNode("nostate", vmUuid);

            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
            return -1;
        }
        return 0;
    }

    /**
     * 重启虚拟机
     *
     * @param vmUuid  虚拟机id
     * @param connect 连接对象
     * @return 0:重启成功 1:重启失败
     */
    @Transactional(rollbackFor = Exception.class)
    public int restartVm(String vmUuid, Connect connect) {
        Domain dom;
        try {
            dom = connect.domainLookupByUUIDString(vmUuid);
            dom.reboot(1);
            if (dom.isActive() != 1) {
//                Node node = nodeMapper.findByVmIdAndStatus(vmUuid, Node.STATUS_ENABLED);
//                HostRecord rHostRecord = hostRecordMapper.findFirstByOrderByHid();
//                //更新父目录id
//                nodeMapper.update(rHostRecord.getHid(), node.getId());
                dom.create();
            }
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
     * @return 0:创建成功 1:创建失败
     */
    @Transactional(rollbackFor = Exception.class)
    public int createVm(VmParms vmParms, Connect connect) {
        String volPath = createDisk(vmParms.getSp(), vmParms.getName() + "_disk", vmParms.getDiskSize(), connect);
        try {
            Domain domain = connect.domainDefineXML(String.format(Const.XMLDESC, vmParms.getName(), vmParms.getMem(), vmParms.getMem(), vmParms.getCpu(), volPath, vmParms.getIsopath()));
            VmRecord vmRecord = new VmRecord();
            Node node = new Node();

            vmRecord.setVmId(domain.getUUIDString());
            vmRecord.setCpuNum(vmParms.getCpu());
            vmRecord.setDiskSize(vmParms.getDiskSize());
            vmRecord.setMemSize(domain.getMaxMemory());
            vmRecord.setIso(vmParms.getIsopath());
            vmRecord.setVmName(vmParms.getName());
            vmRecord.setStates("nostate");
            vmRecord.setVmDesc(vmParms.getVmDesc());
            vmRecordMapper.save(vmRecord);
            node.setId(UUID.randomUUID().toString());
            node.setVmId(domain.getUUIDString());
            node.setStatus(Node.STATUS_ENABLED);
            node.setNodeName(domain.getName());
            node.setClusterId(vmParms.getClusterToBelong());
            //若未激活
            if (domain.isActive() == 0) {
                node.setParentId(vmParms.getClusterToBelong());
            } else if (domain.isActive() == 1) {
                node.setParentId(vmParms.getHostToBelong());
            } else {
                LOGGER.error("虚拟机错误");
            }
            nodeMapper.save(node);

            return 0;
        } catch (LibvirtException e) {
            LOGGER.error("{}", e);
        }
        return -1;
    }


    public VmRecord getVmRecord(String vmId) {
        VmRecord vmRecord = vmRecordMapper.getByVmId(vmId);
        return vmRecord;
    }

    /**
     * 创建磁盘
     *
     * @param sp      存储池
     * @param name    磁盘名
     * @param size    磁盘大小
     * @param connect 连接对象
     * @return 卷路径
     */
    public String createDisk(String sp, String name, long size, Connect connect) {
        try {
            StoragePool pool = connect.storagePoolLookupByName(sp);
            StorageVol vol = pool.storageVolCreateXML(String.format(Const.VOLXML, name, size, name), 0);
            return vol.getPath();
        } catch (LibvirtException e) {
            LOGGER.error("{}", e);
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
        Map<String, String> isos = new HashMap(16);
        try {
            for (String spName : connect.listStoragePools()) {
                StoragePool po = connect.storagePoolLookupByName(spName);
                for (String vName : po.listVolumes()) {
                    StorageVol vol = po.storageVolLookupByName(vName);
                    if (vol.getName().endsWith(".iso")) {
                        isos.put(vol.getName(), vol.getPath());
                    }
                }
            }
        } catch (LibvirtException e) {
            LOGGER.error("获取iso卷列表出错", e);
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
        String[] storagePools = null;
        try {
            storagePools = connect.listStoragePools();
        } catch (LibvirtException e) {
            LOGGER.error("获取存储池字符串列表出错", e);
        }
        return storagePools;
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
        DomainInfo domainInfo = domain.getInfo();
        domainMap.put("uuid", domain.getUUIDString());
        domainMap.put("vmName", domain.getName());
        domainMap.put("memory", domain.getMaxMemory());
        domainMap.put("state", MydomainState.values()[domainInfo.state.ordinal()]);
        domainMap.put("maxCpus", domain.getMaxVcpus());
        domainMap.put("osType", domain.getOSType());
        domainMap.put("cpuInfo", domain.getVcpusInfo());
        domainMap.put("isAlive", domain.isActive());

        //若虚拟机运行中
        if (1 == (int) domainMap.get("isAlive")) {
            DomainJobInfo domainJobInfo = domain.getJobInfo();
            //虚拟机内存使用率
            double memUsedRate = (domainJobInfo.getMemTotal() - domainJobInfo.getMemRemaining()) / domainJobInfo.getMemTotal();
            domainMap.put("memUsedRate", memUsedRate);
        }

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
            LOGGER.error("{}", e);
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
    @Transactional(rollbackFor = Exception.class)
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
            //删除结点表中的记录
            Node node = nodeMapper.findByVmIdAndStatus(vmUuid, Node.STATUS_ENABLED);
            nodeMapper.delete(node);

        } catch (LibvirtException e) {
            LOGGER.error("{}", e);
            return -1;
        } catch (ParserConfigurationException e) {
            LOGGER.error("{}", e);
        } catch (SAXException e) {
            LOGGER.error("{}", e);
        } catch (IOException e) {
            LOGGER.error("{}", e);
        }
        return 0;
    }
}