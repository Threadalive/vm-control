package com.libvirtjava.demo.vm.domain.menu;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/24 10:01
 */
@Entity
public class HostRecord {

    @Id
    private String hid;
    private String hostName;
    private String hostDesc;
    private String ipAddr;
    private Integer cpuNum;
    private Integer memSize;
    private String pwd;
    private Integer memUsed;

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostDesc() {
        return hostDesc;
    }

    public void setHostDesc(String hostDesc) {
        this.hostDesc = hostDesc;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
    }

    public Integer getMemSize() {
        return memSize;
    }

    public void setMemSize(Integer memSize) {
        this.memSize = memSize;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(Integer memUsed) {
        this.memUsed = memUsed;
    }
}
