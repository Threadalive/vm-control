package com.libvirtjava.demo.vm.util;

/**
 * @Description domain状态
 * @Author zhenxing.dong
 * @Date 2019/12/20 11:10
 */
public enum MydomainState {
    nostate,
    running,
    blocked,
    paused,
    shutdown,
    shutoff,
    crashed,
    pmsuspended
}
