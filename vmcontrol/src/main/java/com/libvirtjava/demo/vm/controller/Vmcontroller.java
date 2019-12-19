package com.libvirtjava.demo.vm.controller;

import com.libvirtjava.demo.vm.domain.Host;
import com.libvirtjava.demo.vm.service.HostService;
import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.libvirt.StoragePool;
import org.libvirt.StorageVol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/18 10:39
 */
@Controller
@RequestMapping("/vm/allControl")
public class Vmcontroller {

    Connect connect = null;

    @Autowired
    HostService hostService;

    @GetMapping(params = "host")
    public String host(ModelMap modelMap) {
        connect = getConn();
        Host host = hostService.getHost(connect);
        try {
            String hostName = connect.getHostName();
            host.setHostName(hostName);
        } catch (LibvirtException e) {
            e.printStackTrace();
        }

        modelMap.put("unit", host);
        closeConn(connect);
        return "another/host";
    }

    @GetMapping(params = "Vm")
    public String vm(ModelMap modelMap) {
        connect = getConn();
//        Host host = hostService;
        try {
            String hostName = connect.getHostName();
        } catch (LibvirtException e) {
            e.printStackTrace();
        }

//        modelMap.put("unit", host);
        closeConn(connect);
        return "another/vm";
    }

    public Connect getConn() {
        try {
            connect = new Connect("qemu+ssh://root@192.168.157.134/system", false);
        } catch (LibvirtException e) {
            System.out.println("exception caught:" + e);
            System.out.println(e.getError());
        }
        return connect;
    }

    public void closeConn(Connect connect) {
        try {
            connect.close();
        } catch (LibvirtException e1) {
            e1.printStackTrace();
        }
    }

    public void getInfo() {
        try {

            // 获得inactive状态的虚拟机数量
            System.out.println("numOfDefinedDomains:" + connect.numOfDefinedDomains());
            // 获得inactive状态的虚拟机列表，返回值是String[],虚拟机名称的数组
            System.out.println("listDefinedDomains:" + connect.listDefinedDomains());

            for (int c : connect.listDomains()) {
                System.out.println("  ->" + c);
            }

            for (String c : connect.listStoragePools()) {
                StoragePool po = connect.storagePoolLookupByName(c);
                for (String v : po.listVolumes()) {
                    StorageVol vol = po.storageVolLookupByName(v);
                    System.out.println("‐‐‐‐> " + vol.getName());
                    System.out.println("‐‐‐‐‐‐‐> " + vol.getPath());
                }
            }
        } catch (LibvirtException e) {
            System.out.println("exception caught:" + e);
            System.out.println(e.getError());
        }
    }

}
