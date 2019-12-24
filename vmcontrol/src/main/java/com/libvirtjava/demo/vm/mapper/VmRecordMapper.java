package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.menu.VmRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @param
 * @return
 * @author zhenxing.dong
 */
public interface VmRecordMapper extends JpaRepository<VmRecord,String> {

}
