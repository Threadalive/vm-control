//package com.libvirtjava.demo.parmsutil.util.treeStructure;
//
//import io.swagger.annotations.ApiModelProperty;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Description TODO
// * @Author zhenxing.dong
// * @Date 2019/12/23 23:52
// */
//public class TreeNode<T> {
//    @ApiModelProperty(value = "id", notes = "id")
//    public Integer id;
//
//    @ApiModelProperty(value = "父ID", notes = "父ID")
//    private Integer parentId;
//
//    @ApiModelProperty(value = "子节点", notes = "子节点")
//    private List<T> children = new ArrayList<>();
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Integer parentId) {
//        this.parentId = parentId;
//    }
//
//    public List<T> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<T> children) {
//        this.children = children;
//    }
//}
