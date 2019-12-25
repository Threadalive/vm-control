package com.libvirtjava.demo.vm.domain.menu;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/24 10:05
 */
@Entity
public class VmRecord implements Serializable {

    @Id
    private String vmId;
    private String vmName;
    private String vmDesc;
    private Long memSize;
    private Long diskSize;
    private String storagePath;
    private Integer cpuNum;
    private String ios;
    private String os;
    private Integer memUsed;
    private Integer states;
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

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
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

    public Integer getStates() {
        return states;
    }

    public void setStates(Integer states) {
        this.states = states;
    }

    public Integer getCpuUsed() {
        return cpuUsed;
    }

    public void setCpuUsed(Integer cpuUsed) {
        this.cpuUsed = cpuUsed;
    }
}
