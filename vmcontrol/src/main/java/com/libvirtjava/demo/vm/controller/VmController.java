package com.libvirtjava.demo.vm.controller;

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

import java.util.ArrayList;
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
     * @return 结果集<"isoVolList",Map<卷名，卷路径>>
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
     * @param connect 连接对象
     * @return 0 -1
     */
    @PostMapping(params = "startVm")
    @ResponseBody
    public HashMap<String, String> startVm(String uuid, Connect connect) {
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
     * @param connect 连接对象
     * @return 0 -1
     */
    @PostMapping(params = "stopVm")
    @ResponseBody
    public HashMap<String, String> stopVm(String uuid, Connect connect) {
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
     * @param connect 连接对象
     */
    @PostMapping(params = "closeConn")
    @ResponseBody
    public HashMap<String, String> closeConn(Connect connect) {
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

    @PostMapping(params = "getTree")
    @ResponseBody
    public HashMap<String, List<Node>> getTree(Node node) {
        HashMap<String, List<Node>> resultMap = new HashMap<>(1);
        List<Node> nodeList = new ArrayList<>();
        if (node.getParentId() == null) {
            nodeList = nodeMapper.findByStatus(Node.STATUS_ENABLED);
        } else if ("1".equals(node.getParentId())) {
            nodeList = menuService.getHostAndVmList(node);
        } else {
            nodeList = menuService.getVmList(node);
        }
        resultMap.put("list", nodeList);
        return resultMap;
    }
}
