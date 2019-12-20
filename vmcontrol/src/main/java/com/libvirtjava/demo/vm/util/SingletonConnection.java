package com.libvirtjava.demo.vm.util;

import org.libvirt.Connect;
import org.libvirt.LibvirtException;

/**
 * @Description 单实例Connect对象
 * @Author zhenxing.dong
 * @Date 2019/12/20 12:06
 */
public class SingletonConnection {

    //静态公有工厂方法，返回唯一实例
    public static Connect getInstance(Connect connect) {
        if (connect == null) {
            synchronized (Connect.class) {
                if (connect == null) {
                    try {
                        connect = new Connect("qemu+ssh://root@192.168.157.137/system", false);
                    } catch (LibvirtException e) {
                        System.out.println("exception caught:" + e);
                        System.out.println(e.getError());
                    }
                }
            }
        }
        return connect;
    }
}
