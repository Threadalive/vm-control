package com.libvirtjava.demo.vm.domain.menu;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description 虚拟机信息记录
 *
 * @Author zhenxing.dong
 * @Date 2019/12/24 10:05
 */
@Entity
public class VmRecord implements Serializable {

    @Id
    /**
     * 虚拟机id
     */
    private String vmId;
    /**
     * 虚拟机名
     */
    private String vmName;
    /**
     * 虚拟机描述
     */
    private String vmDesc;
    /**
     * 内存大小
     */
    private Long memSize;
    /**
     * 磁盘大小
     */
    private Long diskSize;
    /**
     * 存储路径
     */
    private String storagePath;
    /**
     * cpu个数
     */
    private Integer cpuNum;
    /**
     * iso卷
     */
    private String iso;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 使用的内存
     */
    private Integer memUsed;
    /**
     * 状态
     */
    private String states;
    /**
     * CPU使用
     */
    private Integer cpuUsed;

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getVmDesc() {
        return vmDesc;
    }

    public void setVmDesc(String vmDesc) {
        this.vmDesc = vmDesc;
    }

    public Long getMemSize() {
        return memSize;
    }

    public void setMemSize(Long memSize) {
        this.memSize = memSize;
    }

    public Long getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(Long diskSize) {
        this.diskSize = diskSize;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public Integer getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Integer getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(Integer memUsed) {
        this.memUsed = memUsed;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public Integer getCpuUsed() {
        return cpuUsed;
    }

    public void setCpuUsed(Integer cpuUsed) {
        this.cpuUsed = cpuUsed;
    }
}
