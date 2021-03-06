package com.libvirtjava.demo.vm.util;

import org.libvirt.Connect;
import org.libvirt.LibvirtException;

/**
 * @Description 单实例Connect对象
 * @Author zhenxing.dong
 * @Date 2019/12/20 12:06
 */
public class SingletonConnection {

    /**
     * 主机ip
     */
    private static String hostAddr = "";

    public static String getHostAddr() {
        return hostAddr;
    }

    public static void setHostAddr(String hostAddr) {
        SingletonConnection.hostAddr = hostAddr;
    }

    /**
     * 静态公有工厂方法，返回唯一实例
     * @param connect 连接
     * @return 连接
     */
    public static Connect getInstance(Connect connect) {
        if (connect == null) {
            synchronized (Connect.class) {
                if (connect == null) {
                    try {
                        if ("".equals(hostAddr) || null == hostAddr) {
                            connect = new Connect("qemu:///system", false);
                        }
                        connect = new Connect("qemu+tcp://root@" + hostAddr + "/system", false);
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
