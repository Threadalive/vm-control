package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.Util.Const;
import com.libvirtjava.demo.vm.domain.Vm;
import com.libvirtjava.demo.vm.domain.VmParms;
import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.libvirt.StoragePool;
import org.libvirt.StorageVol;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public int createVm(VmParms vmParms,Connect connect){
        String volPath = createDisk(vmParms.getSp(), vmParms.getName()+"_disk", vmParms.getDiskSize(),connect);
        try {
            connect.domainDefineXML(String.format(Const.XMLDESC, vmParms.getName(), vmParms.getMem(), vmParms.getMem(), vmParms.getCpu(), volPath, vmParms.getIsopath()));
            return 0;
        } catch (LibvirtException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String createDisk(String sp, String name, long size,Connect connect) {
        try {
            StoragePool pool = connect.storagePoolLookupByName(sp);
            StorageVol vol = pool.storageVolCreateXML(String.format(Const.VOLXML, name, size, name), 0);
            return vol.getPath();
        } catch (LibvirtException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map listIsoVolumes(Connect connect) {
        Map isos = new HashMap();
        try {
            for (String c : connect.listStoragePools()) {
                StoragePool po = connect.storagePoolLookupByName(c);
                for(String v:po.listVolumes()) {
                    StorageVol vol = po.storageVolLookupByName(v);
                    if(vol.getName().endsWith(".iso")) {
                        isos.put(vol.getName(), vol.getPath());
                    }
                }
            }
        } catch (LibvirtException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isos;
    }

}
