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
public class Cluster  {

    public static final int STATUS_DISABLED = 0;

    public static final int STATUS_ENABLED = 1;


    @Column(nullable = false, columnDefinition="int COMMENT '唯一标识'")
    @Id
    private Integer cid;

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '集群名称'")
    private String clusterName;

    @Column(nullable = false, columnDefinition = "varchar(1000) COMMENT '集群描述'")
    private String clusterDesc;

    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '状态（1：可用，0：禁用）'")
    private Integer status;

//    @Column(nullable = false, columnDefinition = "bigint(20) COMMENT '归属人ID'")
//    private Long userId;

    @Column(columnDefinition = "int COMMENT '父ID'")
    private Integer parentId;

    private List<Cluster> childrens;

    public List<Cluster> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Cluster> childrens) {
        this.childrens = childrens;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterDesc() {
        return clusterDesc;
    }

    public void setClusterDesc(String clusterDesc) {
        this.clusterDesc = clusterDesc;
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