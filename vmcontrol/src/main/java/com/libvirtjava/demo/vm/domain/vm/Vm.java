package com.libvirtjava.demo.vm.domain.vm;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/18 16:20
 */
public class Vm {
    private String name;
    private Integer CPU;
    private Long memory;
    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCPU() {
        return CPU;
    }

    public void setCPU(Integer CPU) {
        this.CPU = CPU;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
