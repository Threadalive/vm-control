package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.domain.menu.HostRecord;
import com.libvirtjava.demo.vm.domain.menu.Node;
import com.libvirtjava.demo.vm.domain.parmsutil.Host;
import com.libvirtjava.demo.vm.mapper.HostRecordMapper;
import com.libvirtjava.demo.vm.mapper.NodeMapper;
import com.libvirtjava.demo.vm.util.Const;
import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.libvirt.NodeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 主机操纵服务
 * @Author zhenxing.dong
 * @Date 2019/12/18 10:49
 */
@Service
@Lazy
public class HostService {

    /**
     * 虚拟机操作服务
     */
    @Autowired
    private VmService vmService;

    /**
     * 主机记录操作服务
     */
    @Autowired
    private HostRecordMapper hostRecordMapper;

    /**
     * 结点操作
     */
    @Autowired
    private NodeMapper nodeMapper;

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(HostService.class);

    /**
     * 获取主机信息
     *
     * @param connect 连接对象
     * @return 主机对象
     */
    public Host getHost(Connect connect) {
        Host host = new Host();
        try {
            //获取主机架构信息
            NodeInfo nodeInfo = connect.nodeInfo();
            host.setHostName(connect.getHostName());
            host.setArth(nodeInfo.model);
            host.setMemory(nodeInfo.memory >> 10);
            host.setNumOfCpu(nodeInfo.cpus);
            host.setType(connect.getType());
            host.setDomainList(vmService.getDomainList(connect));

        } catch (LibvirtException e) {
            LOGGER.error("{}", e);
        }
        return host;
    }

    /**
     * 根据主机id获取一个主机信息对象
     *
     * @param hostId 主机id
     * @return 主机记录
     */
    public HostRecord getHostMsgByHostId(String hostId) {
        return hostRecordMapper.getHostRecordByHid(hostId);
    }

    /**
     * 获取主机列表
     *
     * @return 主机信息列表
     */
    public List<HostRecord> getAllHostRecords() {
        List<HostRecord> hostRecords;
        hostRecords = (List<HostRecord>) hostRecordMapper.findAll();

        return hostRecords;
    }

    /**
     * 添加主机记录
     *
     * @param hostRecord 主机记录对象
     * @return 添加结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> addHostRecord(HostRecord hostRecord, String clusterName, Connect connect) {

        try {
            String hostName = connect.getHostName();
            //主机结点信息
            NodeInfo nodeInfo = connect.nodeInfo();

            hostRecord.setCpuNum(nodeInfo.maxCpus());

            //内存占用
            hostRecord.setMemUsed(nodeInfo.memory);

            hostRecord.setHostName(hostName);

        } catch (LibvirtException e) {
            LOGGER.error("{}", e);
        }
        Map<String, Object> resultMap = new HashMap<>(1);
        Node hostNode = new Node();
        Node clusterNode = nodeMapper.findByNodeNameAndStatus(clusterName, Node.STATUS_ENABLED);
        try {
            hostRecord.setHid(UUID.randomUUID().toString());
            hostRecordMapper.save(hostRecord);
            //设置结点id
            hostNode.setId(UUID.randomUUID().toString());
            //设置所属集群id
            hostNode.setClusterId(clusterNode.getId());
            //设置父节点id
            hostNode.setParentId(clusterNode.getId());
            hostNode.setStatus(Node.STATUS_ENABLED);
            //设置主机id
            hostNode.setHostId(UUID.randomUUID().toString());
            //设置名字描述
            hostNode.setNodeName(hostRecord.getHostName());
            hostNode.setNodeDesc(hostRecord.getHostDesc());

            //保存主机结点
            nodeMapper.save(hostNode);

            resultMap.put(Const.MSG, Const.SUCCEED);
        } catch (Exception e) {
            resultMap.put(Const.MSG, Const.FAIL);
            LOGGER.error("{}", e);
        }
        return resultMap;
    }

    /**
     * 删除主机记录
     *
     * @param hostRecord 记录对象
     * @param connect    连接
     * @return 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteHostRecord(HostRecord hostRecord, Connect connect) {
        Map<String, Object> resultMap = new HashMap<>(1);

        try {
            //根据主机id获取结点
            Node hostNode = nodeMapper.findByHostIdAndStatus(hostRecord.getHid(), Node.STATUS_ENABLED);

            List<Node> vmList = nodeMapper.findByParentIdAndStatus(hostNode.getId(), Node.STATUS_ENABLED);
            for (Node n : vmList) {
                //删除该虚拟机，以及在表中的记录
                vmService.deleteVm(n.getVmId(), true, connect);
                nodeMapper.delete(n);
            }
            nodeMapper.delete(hostNode);
            resultMap.put(Const.MSG, Const.SUCCEED);
        } catch (Exception e) {
            resultMap.put(Const.MSG, Const.FAIL);
            LOGGER.error("{}", e);
        }
        return resultMap;
    }

}
