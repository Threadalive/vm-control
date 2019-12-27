package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.menu.HostRecord;
import org.springframework.data.repository.CrudRepository;


/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/20 16:27
 */
public interface HostRecordMapper extends CrudRepository<HostRecord, String> {
    HostRecord findFirstByOrderByHid();

    HostRecord getHostRecordByHid(String hid);

}
