package com.libvirtjava.demo.vm.controller;

import com.libvirtjava.demo.vm.util.SingletonConnection;
import com.libvirtjava.demo.vm.domain.Host;
import com.libvirtjava.demo.vm.domain.VmParms;
import com.libvirtjava.demo.vm.service.HostService;
import com.libvirtjava.demo.vm.service.VmService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * @param uuid    虚拟机id
     * @param connect 连接对象
     * @return 0 -1
     */
    @PostMapping(params = "startVm")
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
    public int stopVm(String uuid, Connect connect) {
        return vmService.stopVm(uuid, connect);
    }

    /**
     * 创建一个虚拟机
     *
     * @param vmParms 给定虚拟机参数列表
     */
    @PostMapping(params = "createVm")
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
            System.out.println(e);
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
        } catch (LibvirtException e1) {
            result = "fail";
            System.out.println(e1);
        }

        return result;
    }

//    public void getInfo() {
//        try {
//
//            // 获得inactive状态的虚拟机数量
//            System.out.println("numOfDefinedDomains:" + connect.numOfDefinedDomains());
//            // 获得inactive状态的虚拟机列表，返回值是String[],虚拟机名称的数组
//            System.out.println("listDefinedDomains:" + connect.listDefinedDomains());
//
//            for (int c : connect.listDomains()) {
//                System.out.println("  ->" + c);
//            }
//
//            for (String c : connect.listStoragePools()) {
//                StoragePool po = connect.storagePoolLookupByName(c);
//                for (String v : po.listVolumes()) {
//                    StorageVol vol = po.storageVolLookupByName(v);
//                    System.out.println("‐‐‐‐> " + vol.getName());
//                    System.out.println("‐‐‐‐‐‐‐> " + vol.getPath());
//                }
//            }
//        } catch (LibvirtException e) {
//            System.out.println("exception caught:" + e);
//            System.out.println(e.getError());
//        }
//    }

}
