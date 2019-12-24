package com.libvirtjava.demo.vm.controller;

import com.libvirtjava.demo.vm.domain.menu.Node;
import com.libvirtjava.demo.vm.mapper.NodeMapper;
import com.libvirtjava.demo.vm.service.MenuService;
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
     * 虚拟机信息列表
     *
     * @param modelMap 模型
     * @return 虚拟机列表页
     */
    @GetMapping(params = "Vm")
    public String vm(ModelMap modelMap) {
        modelMap.put("isoVolList", vmService.listIsoVolumes(connect));
        modelMap.put("isoStoList", vmService.listStoragePools(connect));

        return "kvm/vm";
    }

    /**
     * 启动虚拟机
     *
     * @param uuid  虚拟机id
     * @param connect 连接对象
     * @return 0 -1
     */
    @PostMapping(params = "startVm")
    @ResponseBody
    public int startVm(String uuid, Connect connect) {
        return vmService.startVm(uuid, connect);
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
    public int stopVm(String uuid, Connect connect) {
        return vmService.stopVm(uuid, connect);
    }

    /**
     * 创建一个虚拟机
     *
     * @param vmParms 给定虚拟机参数列表
     */
    @PostMapping(params = "createVm")
    @ResponseBody
    public void createVm(VmParms vmParms) {
        vmService.createVm(vmParms, connect);
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
    public int startVm(String vmUuid, boolean deleteDisk) {
        return vmService.deleteVm(vmUuid, deleteDisk, connect);
    }

    /**
     * 获取一个链接
     */
    @PostMapping(params = "getConn")
    @ResponseBody
    public String getConn() {
        String result = null;
        try {
            connect = SingletonConnection.getInstance(connect);
            result = "succeed";
        } catch (Exception e) {
            result = "fail";
            LOGGER.info("{}",e);
        }
        return result;
    }

    /**
     * 关闭连接
     *
     * @param connect 连接对象
     */
    @PostMapping(params = "closeConn")
    @ResponseBody
    public String closeConn(Connect connect) {
        String result = null;
        try {
            connect.close();
            result = "succeed";
        } catch (LibvirtException e) {
            result = "fail";
            LOGGER.info("{}",e);
        }
        return result;
    }

    @PostMapping(params = "getTree")
    @ResponseBody
    public HashMap<String,List<Node>> getTree(Node node) {
        HashMap<String,List<Node>> resultMap = new HashMap<>(1);
        List<Node> nodeList = new ArrayList<>();
        if (node.getParentId() == null) {
            nodeList = nodeMapper.findByStatus(Node.STATUS_ENABLED);
        }else if("1".equals(node.getParentId())){
            nodeList = menuService.getHostAndVmList(node);
        }else {
            nodeList = menuService.getVmList(node);
        }
        resultMap.put("list",nodeList);

        return resultMap;
    }
}
