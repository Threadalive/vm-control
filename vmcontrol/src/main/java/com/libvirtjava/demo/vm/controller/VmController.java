package com.libvirtjava.demo.vm.controller;

import com.libvirtjava.demo.vm.domain.menu.HostRecord;
import com.libvirtjava.demo.vm.domain.menu.Node;
import com.libvirtjava.demo.vm.mapper.NodeMapper;
import com.libvirtjava.demo.vm.service.MenuService;
import com.libvirtjava.demo.vm.util.Const;
import com.libvirtjava.demo.vm.util.SingletonConnection;
import com.libvirtjava.demo.vm.domain.vm.Host;
import com.libvirtjava.demo.vm.domain.vm.VmParms;
import com.libvirtjava.demo.vm.service.HostService;
import com.libvirtjava.demo.vm.service.VmService;
import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/18 10:39
 */
@Controller
@RequestMapping("/vm/allControl")
public class VmController {

    /**
     * 静态私有成员变量
     */
    private static volatile Connect connect;

    /**
     * 日志
     */
    private Logger LOGGER = LoggerFactory.getLogger(VmController.class);

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private MenuService menuService;
    /**
     * 主机信息操作服务
     */
    @Autowired
    HostService hostService;

    /**
     * 虚拟机信息操作服务
     */
    @Autowired
    VmService vmService;


    /**
     * @param modelMap 模型对象
     * @return 返回主机信息页面
     */
//    @RequiresRoles("admin")
    @GetMapping(params = "host")
    public String host(ModelMap modelMap) {
        Host host = hostService.getHost(connect);

        modelMap.put("hostMsg", host);

        return "kvm/host";
    }

    /**
     * iso卷信息列表
     *
     * @return 结果集<"isoVolList",Map<卷名，卷路径>>
     */
    @PostMapping(params = "getIsoMsg")
    @ResponseBody
    public HashMap<String, Map<String,String>> isoMsg() {
        HashMap<String, Map<String,String>> resultMap = new HashMap<>(1);
        resultMap.put("isoVolList", vmService.listIsoVolumes(connect));
        return resultMap;
    }

    /**
     * 获取存储池名数组
     *
     * @return 结果集<"spsNameList",String[name]>
     */
    @PostMapping(params = "getStoragePoolsMsg")
    @ResponseBody
    public HashMap<String, String[]> spsMsg() {
        HashMap<String, String[]> resultMap = new HashMap<>(1);
        resultMap.put("spsNameList", vmService.listStoragePools(connect));
        return resultMap;
    }

    /**
     * 启动虚拟机
     *
     * @param uuid    虚拟机id
     * @return 0 -1
     */
    @PostMapping(params = "startVm")
    @ResponseBody
    public HashMap<String, String> startVm(String uuid) {
        HashMap<String, String> resultMap = new HashMap<>(1);

        int result = vmService.startVm(uuid, connect);
        if (result == 0) {
            resultMap.put(Const.MSG, Const.SUCCEED);
        }else {
            resultMap.put(Const.MSG, Const.FAIL);
        }
        return resultMap;
    }

    /**
     * 关闭虚拟机
     *
     * @param uuid    虚拟机id
     * @return 0 -1
     */
    @PostMapping(params = "stopVm")
    @ResponseBody
    public HashMap<String, String> stopVm(String uuid) {
        HashMap<String, String> resultMap = new HashMap<>(1);

        int result = vmService.stopVm(uuid, connect);
        if (result == 0) {
            resultMap.put(Const.MSG, Const.SUCCEED);
        }else {
            resultMap.put(Const.MSG, Const.FAIL);
        }
        return resultMap;
    }

    /**
     * 创建一个虚拟机
     *
     * @param vmParms 给定虚拟机参数列表
     */
    @PostMapping(params = "createVm")
    @ResponseBody
    public HashMap<String, String> createVm(VmParms vmParms) {
        HashMap<String, String> resultMap = new HashMap<>(1);
        int result = vmService.createVm(vmParms, connect);
        if (result == 0) {
            resultMap.put(Const.MSG, Const.SUCCEED);
        }else {
            resultMap.put(Const.MSG, Const.FAIL);
        }
        return resultMap;
    }

    /**
     * 删除虚拟机
     *
     * @param vmUuid     虚拟机id
     * @param deleteDisk 是否删除磁盘
     * @return 0 -1
     */
    @PostMapping(params = "deleteVm")
    @ResponseBody
    public HashMap<String, String> deleteVm(String vmUuid, boolean deleteDisk) {
        HashMap<String, String> resultMap = new HashMap<>(1);

        int result = vmService.deleteVm(vmUuid, deleteDisk, connect);

        if (result == 0) {
            resultMap.put(Const.MSG, Const.SUCCEED);
        }else {
            resultMap.put(Const.MSG, Const.FAIL);
        }
        return resultMap;
    }

    /**
     * 获取一个链接
     */
    @PostMapping(params = "getConn")
    @ResponseBody
    public HashMap<String, String> getConn() {
        HashMap<String, String> resultMap = new HashMap<>(1);
        try {
            connect = SingletonConnection.getInstance(connect);
            resultMap.put(Const.MSG, Const.SUCCEED);
        } catch (Exception e) {
            resultMap.put(Const.MSG, Const.FAIL);
            LOGGER.info("{}", e);
        }
        return resultMap;
    }

    /**
     * 关闭连接
     *
     */
    @PostMapping(params = "closeConn")
    @ResponseBody
    public HashMap<String, String> closeConn() {
        HashMap<String, String> resultMap = new HashMap<>(1);
        try {
            connect.close();
            resultMap.put(Const.MSG, Const.SUCCEED);
        } catch (LibvirtException e) {
            resultMap.put(Const.MSG, Const.FAIL);
            LOGGER.info("{}", e);
        }
        return resultMap;
    }

    /**
     * 获取结点列表，当选项为虚拟机时，返回虚拟机信息
     *
     * @param node 当前选中结点
     * @return 结点列表或vm信息Map
     */
    @PostMapping(params = "getTree")
    @ResponseBody
    public Map<String, Object> getTree(Node node) {
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Node> nodeList;
        if (node.getParentId() == null) {
            nodeList = nodeMapper.getClusterList(null);
        } else if ("0".equals(node.getParentId())) {
            nodeList = menuService.getHostAndVmList(node);
        } else {
            Domain domain = null;
            //结点为vm
            if (null != node.getVmId()){
                //获取vm信息
                nodeList = null;
                try {
                    //根据uuid查询对应domain
                    domain = connect.domainLookupByUUIDString(node.getVmId());
                    Map<String,Object> domainMap = vmService.convertDomainInfo(domain);
                    resultMap.put("vmMsg",domainMap);
                } catch (LibvirtException e) {
                    LOGGER.error("{}",e);
                }
            }else {
                //结点为主机,则获取他的从属vm结点
                nodeList = menuService.getVmList(node);
            }
        }
        resultMap.put("nodeList", nodeList);
        return resultMap;
    }

    /**
     * 删除集群
     * @param clusterNode 要删除的集群结点
     * @return 删除结果
     */
    @PostMapping(params = "deleteCluster")
    @ResponseBody
    public Map<String, Object> deleteCluster(Node clusterNode) {
        LOGGER.info("删除集群结点",clusterNode.getNoderName());

        return menuService.deleteCluster(clusterNode,connect);
    }

    /**
     * 添加集群
     * @param clusterNode 要添加的集群结点
     * @return 添加结果
     */
    @PostMapping(params = "addCluster")
    @ResponseBody
    public Map<String, Object> addCluster(Node clusterNode) {
        LOGGER.info("添加集群结点",clusterNode.getNoderName());

        return menuService.addCluster(clusterNode);
    }

    /**
     * 添加主机记录
     * @param hostRecord 要添加的集群结点
     * @return 添加结果
     */
    @PostMapping(params = "addHost")
    @ResponseBody
    public Map<String, Object> addHost(HostRecord hostRecord,String clusterName) {
        LOGGER.info("添加主机记录",hostRecord.getHostName());

        return hostService.addHostRecord(hostRecord,clusterName);
    }

    /**
     * 删除主机记录
     * @param hostRecord 要删除的集群结点
     * @return 删除结果
     */
    @PostMapping(params = "deleteHost")
    @ResponseBody
    public Map<String, Object> deleteHostRecord(HostRecord hostRecord) {
        LOGGER.info("删除主机记录",hostRecord.getHostName());

        return hostService.deleteHostRecord(hostRecord,connect);
    }

    /**
     * 获取当前连接主机信息
     * @return 当前连接主机信息
     */
    @PostMapping(params = "getHostMsg")
    @ResponseBody
    public Map<String, Object> getHostMsg() {
        Map<String, Object> resultMap = new HashMap<>(1);

        Host host = hostService.getHost(connect);
        resultMap.put(Const.MSG,host);
        return resultMap;
    }
    /**
     * 获取记录的主机列表信息
     * @return 记录的主机列表
     */
    @PostMapping(params = "getHostMsgList")
    @ResponseBody
    public Map<String, Object> getHostMsgList() {
        Map<String, Object> resultMap = new HashMap<>(1);

        List<HostRecord> hosts = hostService.getAllHostRecords();
        resultMap.put(Const.MSG,hosts);
        return resultMap;
    }
}
