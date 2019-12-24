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
@Table(name = "cluster")
public class Node {

    public static final int STATUS_DISABLED = 0;

    public static final int STATUS_ENABLED = 1;


    @Column(nullable = false, columnDefinition="int COMMENT '唯一标识'")
    @Id
    private Integer id;

    private String vmId;

    private String hostId;

    private String clusterId;

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '结点名称'")
    private String noderName;

    @Column(nullable = false, columnDefinition = "varchar(1000) COMMENT '结点描述'")
    private String nodeDesc;

    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '状态（1：可用，0：禁用）'")
    private Integer status;

//    @Column(nullable = false, columnDefinition = "bigint(20) COMMENT '归属人ID'")
//    private Long userId;

    @Column(columnDefinition = "int COMMENT '父ID'")
    private Integer parentId;

//    private List<Node> childrens;

//    public List<Node> getChildrens() {
//        return childrens;
//    }
//
//    public void setChildrens(List<Node> childrens) {
//        this.childrens = childrens;
//    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return noderName;
    }

    public void setNoderName(String noderName) {
        this.noderName = noderName;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}