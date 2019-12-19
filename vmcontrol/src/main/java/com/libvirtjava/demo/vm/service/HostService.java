package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.domain.Host;
import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.libvirt.NodeInfo;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/18 10:49
 */
@Service
public class HostService {

    public Host getHost(Connect connect){
        Host host = new Host();
        try {
            NodeInfo nodeInfo = connect.nodeInfo();
            host.setHostName(connect.getHostName());
            host.setArth(nodeInfo.model);
            host.setMemory(nodeInfo.memory);
            host.setNumOfCpu(nodeInfo.cpus);
            host.setType(connect.getType());
        } catch (LibvirtException e) {
            e.printStackTrace();
        }
        return host;
    }

}
