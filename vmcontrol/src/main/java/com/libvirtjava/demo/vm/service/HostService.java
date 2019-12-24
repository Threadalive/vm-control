package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.domain.menu.HostRecord;
import com.libvirtjava.demo.vm.domain.vm.Host;
import com.libvirtjava.demo.vm.mapper.HostRecordMapper;
import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.libvirt.NodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/18 10:49
 */
@Service
public class HostService {

    @Autowired
    private VmService vmService;

    @Autowired
    private HostRecordMapper hostRecordMapper;
    /**
     * 获取主机信息
     * @param connect 连接对象
     * @return 主机对象
     */
    public Host getHost(Connect connect){
        Host host = new Host();
        try {
            //获取主机架构信息
            NodeInfo nodeInfo = connect.nodeInfo();
            host.setHostName(connect.getHostName());
            host.setArth(nodeInfo.model);
            host.setMemory(nodeInfo.memory>>10);
            host.setNumOfCpu(nodeInfo.cpus);
            host.setType(connect.getType());
            host.setDomainList(vmService.getDomainList(connect));

        } catch (LibvirtException e) {
            e.printStackTrace();
        }
        return host;
    }

    /**
     *
     * @return
     */
    public HostRecord getRandomHost(){
        return hostRecordMapper.findFirstByOrOrderByHid();
    }
}
