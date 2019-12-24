package com.libvirtjava.demo.vm.domain.vm;

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
