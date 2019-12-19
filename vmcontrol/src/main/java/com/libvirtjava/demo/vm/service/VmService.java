package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.domain.Vm;
import org.libvirt.Connect;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/18 16:22
 */
@Service
public class VmService {
    public List<Vm> getVms(Connect connect){
        List<Vm> vmList = new ArrayList<>();
//        String[] domainNames = connect.listDefinedDomains();
//        for (String name:domainNames){
//            vmList.add(new Vm().setName(name));
//        }
//    }
        return null;
}
}
