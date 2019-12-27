package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.menu.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/20 16:27
 */
public interface NodeMapper extends JpaRepository<Node, String> {

    List<Node> findByParentIdAndStatus(String parentId, int status);

    Node findByVmIdAndStatus(String vmId, int status);

    Node findByNodeNameAndStatus(String nodeName, int status);

    Node findByHostIdAndStatus(String hostId, int status);

    List<Node> getNodesByParentId(String parentId);

    @Query("select n from Node n where n.parentId <> ?1 and n.vmId is null")
    List<Node> getHostList(String parentId);

    List<Node> getByVmIdNotNull();

    @Modifying
    @Query("UPDATE Node n set n.parentId = :parentId where n.id = :id")
    void updateNode(String parentId, String id);
}
