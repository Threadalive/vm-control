package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.menu.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @param
 * @return
 * @author zhenxing.dong
 */
public interface NodeMapper extends JpaRepository<Node,Integer> {

    List<Node> findByParentIdAndStatus(int parentId, int status);

    List<Node> findByStatus(int status);
}
