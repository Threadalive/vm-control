package com.libvirtjava.demo.vm.domain.parmsutil;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/19 15:40
 */
public class VmParms {

    String name;
    int cpu;
    long mem;
    long diskSize;
    String isopath;
    String sp;
    String clusterToBelong;
    String hostToBelong;
    String vmDesc;

    public String getVmDesc() {
        return vmDesc;
    }

    public void setVmDesc(String vmDesc) {
        this.vmDesc = vmDesc;
    }

    public String getHostToBelong() {
        return hostToBelong;
    }

    public void setHostToBelong(String hostToBelong) {
        this.hostToBelong = hostToBelong;
    }

    public String getClusterToBelong() {
        return clusterToBelong;
    }

    public void setClusterToBelong(String clusterToBelong) {
        this.clusterToBelong = clusterToBelong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public long getMem() {
        return mem;
    }

    public void setMem(long mem) {
        this.mem = mem;
    }

    public long getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(long diskSize) {
        this.diskSize = diskSize;
    }

    public String getIsopath() {
        return isopath;
    }

    public void setIsopath(String isopath) {
        this.isopath = isopath;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }
}
