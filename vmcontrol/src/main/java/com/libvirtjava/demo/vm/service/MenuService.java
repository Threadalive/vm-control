package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.domain.menu.ClusterVo;
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

    private List<ClusterVo> getTree(List<ClusterVo> list) {

        Map<Integer, ClusterVo> dtoMap = new HashMap<>();
        for (ClusterVo node : list) {
            dtoMap.put(node.getId(), node);
        }
        List<ClusterVo> resultList = new ArrayList<>();
        for (Map.Entry<Integer, ClusterVo> entry : dtoMap.entrySet()) {
            ClusterVo node = entry.getValue();
            if (node.getParentId() == null) {
                // 如果是顶层节点，直接添加到结果集合中
                resultList.add(node);
            } else {
                // 如果不是顶层节点，找其父节点，并且添加到父节点的子节点集合中
                if (dtoMap.get(Long.valueOf(node.getParentId())) != null) {
                    dtoMap.get(Long.valueOf(node.getParentId())).getChildren().add(node);
                }
            }
        }
        return resultList;
    }
}
