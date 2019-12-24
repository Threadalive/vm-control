package com.libvirtjava.demo.vm.domain.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/24 00:07
 */
@Entity
@Table(name = "node")
public class Node {

    public static final int STATUS_DISABLED = 0;

    public static final int STATUS_ENABLED = 1;


    @Column(nullable = false, columnDefinition="varchar(255) COMMENT '唯一标识'")
    @Id
    private String id;

    private String vmId;

    private String hostId;

    /**
     * 若为集群，则为集群本身id，否则为主机或虚拟机从属集群id
     */
    private String clusterId;

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '结点名称'")
    private String nodeName;

    @Column(nullable = false, columnDefinition = "varchar(1000) COMMENT '结点描述'")
    private String nodeDesc;

    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '状态（1：可用，0：禁用）'")
    private Integer status;

    @Column(columnDefinition = "varchar(255) COMMENT '父ID'")
    private String parentId;



//    private List<Node> childrens;

//    public List<Node> getChildrens() {
//        return childrens;
//    }
//
//    public void setChildrens(List<Node> childrens) {
//        this.childrens = childrens;
//    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getNoderName() {
        return nodeName;
    }

    public void setNoderName(String noderName) {
        this.nodeName = noderName;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}