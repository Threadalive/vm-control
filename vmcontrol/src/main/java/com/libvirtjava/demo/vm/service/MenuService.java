package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.domain.menu.Node;
import com.libvirtjava.demo.vm.domain.menu.VmRecord;
import com.libvirtjava.demo.vm.mapper.NodeMapper;
import com.libvirtjava.demo.vm.mapper.VmRecordMapper;
import com.libvirtjava.demo.vm.util.Const;
import org.libvirt.Connect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/24 00:23
 */
@Service
public class MenuService {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private VmService vmService;

    @Autowired
    private VmRecordMapper vmRecordMapper;

    private static Logger LOGGER = LoggerFactory.getLogger(MenuService.class);

    public List<Node> getHostAndVmList(Node node){
        List<Node> nodeList = new ArrayList<>();
            nodeList = nodeMapper.findByParentIdAndStatus(node.getId(),Node.STATUS_ENABLED);
        return nodeList;
    }

    public List<Node> getVmList(Node node){
        List<Node> nodeList = new ArrayList<>();
            nodeList = nodeMapper.findByParentIdAndStatus(node.getParentId(),Node.STATUS_ENABLED);
        return nodeList;
    }

    /**
     * 添加集群结点
     * @param clusterNode 集群结点
     * @return 添加结果
     */
    public Map<String,Object> addCluster(Node clusterNode){
        Map<String,Object> resultMap = new HashMap<>(1);

        clusterNode.setStatus(Node.STATUS_ENABLED);
        //集群结点parentId为"0"
        clusterNode.setParentId("0");
        try {
            nodeMapper.save(clusterNode);
            resultMap.put(Const.MSG,Const.SUCCEED);
        } catch (Exception e) {
            resultMap.put(Const.MSG,Const.FAIL);
            LOGGER.error("{}",e);
        }
        return resultMap;
    }

    /**
     * 删除集群结点
     * @param clusterNode 要删除的集群结点
     * @return 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> deleteCluster(Node clusterNode, Connect connect){
        Map<String,Object> resultMap = new HashMap<>(1);
        try {
            //首先删除以此集群结点为父节点的主机和虚拟机
            List<Node> nodeList = nodeMapper.findByParentIdAndStatus(clusterNode.getId(),Node.STATUS_ENABLED);
            for (Node n:nodeList){
                //vmId为空，那么为主机结点，关闭其从属虚拟机
                if (null == n.getVmId()){
                    List<Node> vmList = nodeMapper.findByParentIdAndStatus(n.getId(),Node.STATUS_ENABLED);
                    for (Node n1:vmList){
                        String vmId = n1.getVmId();
                        //关闭并删除该虚拟机
                        vmService.stopVm(vmId,connect);
                        vmService.deleteVm(vmId,true,connect);
                    }
                    //删除该主机结点在结点表中的记录
                    nodeMapper.delete(n);
                }else {
                    //vmId不为空，该结点为虚拟机，删除虚拟机
                    vmService.deleteVm(n.getVmId(),true,connect);
                    //在结点表和记录表中删除
                    nodeMapper.delete(n);
                    VmRecord vmRecord = vmRecordMapper.getOne(n.getVmId());
                    vmRecordMapper.delete(vmRecord);
                }
            }
            //最后删除该集群结点
            nodeMapper.delete(clusterNode);
            resultMap.put(Const.MSG,Const.SUCCEED);
        } catch (Exception e) {
            resultMap.put(Const.MSG,Const.FAIL);
            LOGGER.error("{}",e);
        }
        return resultMap;
    }

}
