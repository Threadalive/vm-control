package com.libvirtjava.demo.vm.domain.menu;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description 主机信息记录
 *
 * @Author zhenxing.dong
 * @Date 2019/12/24 10:01
 */
@Entity
public class HostRecord implements Serializable {

    @Id
    /**
     * 主机id
     */
    private String hid;
    /**
     * 主机名
     */
    private String hostName;
    /**
     * 主机描述
     */
    private String hostDesc;
    /**
     * ip地址
     */
    private String ipAddr;
    /**
     * cpu数量
     */
    private Integer cpuNum;
    /**
     * 内存大小
     */
    private Long memSize;
    /**
     * 主机密码
     */
    private String pwd;
    /**
     * 内存使用情况
     */
    private Long memUsed;

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

    public Long getMemSize() {
        return memSize;
    }

    public void setMemSize(Long memSize) {
        this.memSize = memSize;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Long getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(Long memUsed) {
        this.memUsed = memUsed;
    }
}
