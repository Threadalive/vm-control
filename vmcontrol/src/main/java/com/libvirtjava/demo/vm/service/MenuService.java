package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.domain.menu.Node;
import com.libvirtjava.demo.vm.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Node> getHostAndVmList(Node node){
        List<Node> nodeList = new ArrayList<>();
            nodeList = nodeMapper.findByParentIdAndStatus(node.getParentId(),Node.STATUS_ENABLED);
        return nodeList;
    }

    public List<Node> getVmList(Node node){
        List<Node> nodeList = new ArrayList<>();
            nodeList = nodeMapper.findByParentIdAndStatus(node.getParentId(),Node.STATUS_ENABLED);
        return nodeList;    }
}
