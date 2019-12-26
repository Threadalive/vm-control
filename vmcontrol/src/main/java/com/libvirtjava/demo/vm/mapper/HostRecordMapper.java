package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.menu.HostRecord;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @param
 * @return
 * @author zhenxing.dong
 */
public interface HostRecordMapper extends CrudRepository<HostRecord,String> {
    HostRecord findFirstByOrderByHid();

    HostRecord getHostRecordByHid(String hid);

}
