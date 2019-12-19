package com.libvirtjava.demo.vm.domain;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/18 10:52
 */
public class Host {
    private String hostName;
    private String arth;
    private Integer numOfCpu;
    private Long memory;
    private String type;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getArth() {
        return arth;
    }

    public void setArth(String arth) {
        this.arth = arth;
    }

    public Integer getNumOfCpu() {
        return numOfCpu;
    }

    public void setNumOfCpu(Integer numOfCpu) {
        this.numOfCpu = numOfCpu;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
