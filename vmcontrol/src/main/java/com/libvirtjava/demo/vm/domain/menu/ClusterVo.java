package com.libvirtjava.demo.vm.domain.menu;

import com.libvirtjava.demo.vm.util.treeStructure.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/24 00:13
 */
@ApiModel(value = "集群VO")
public class ClusterVo extends TreeNode implements Serializable {
    @ApiModelProperty(value = "集群名称", notes = "集群名称")
    public String clusterName;

    @ApiModelProperty(value = "集群描述", notes = "集群描述")
    private String clusterDesc;

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
}
