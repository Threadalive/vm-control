package com.libvirtjava.demo.vm.domain.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description 集群、主机、虚拟机结点
 * @Author zhenxing.dong
 * @Date 2019/12/24 00:07
 */
@Entity
@Table(name = "node")
public class Node implements Serializable {

    /**
     * 禁用
     */
    public static final int STATUS_DISABLED = 0;

    /**
     * 启用
     */
    public static final int STATUS_ENABLED = 1;


    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '唯一标识'")
    @Id
    /**
     * 结点id
     */
    private String id;

    /**
     * 虚拟机id
     */
    private String vmId;

    /**
     * 主机id
     */
    private String hostId;

    /**
     * 若为集群，则为集群本身id，否则为主机或虚拟机从属集群id
     */
    private String clusterId;

    /**
     * 结点名
     */
    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '结点名称'")
    private String nodeName;

    /**
     * 结点描述
     */
    @Column(nullable = false, columnDefinition = "varchar(1000) COMMENT '结点描述'")
    private String nodeDesc;

    /**
     * 结点状态
     */
    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '状态（1：可用，0：禁用）'")
    private Integer status;

    /**
     * 父节点id
     */
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

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String noderName) {
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