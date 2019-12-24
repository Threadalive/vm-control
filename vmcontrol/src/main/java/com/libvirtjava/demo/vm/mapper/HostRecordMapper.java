package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.menu.HostRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @param
 * @return
 * @author zhenxing.dong
 */
public interface HostRecordMapper extends JpaRepository<HostRecord,String> {
    HostRecord findFirstByOrderByHid();
}
