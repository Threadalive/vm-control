package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.menu.Node;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @param
 * @return
 * @author zhenxing.dong
 */
public interface NodeMapper extends JpaRepository<Node,String> {

    List<Node> findByParentIdAndStatus(String parentId, int status);

    Node findByVmIdAndStatus(String vmId,int status);

    Node findByNodeNameAndStatus(String nodeName,int status);

    Node findByHostIdAndStatus(String hostId,int status);

    @Query("select n from Node n where n.parentId is null ")
    List<Node> getClusterList();

    @Query("select n from Node n where n.parentId not like ?1 and n.vmId is null")
    List<Node> getHostList(String parentId);

    List<Node> getByVmIdNotNull();

    @Modifying
    @Query("UPDATE Node n set n.parentId =: parentId where n.id =: id")
    void update(@Param(value = "parentId") String parentId, @Param(value = "id")String id);
}
