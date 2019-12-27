package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.menu.VmRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/20 16:27
 */
public interface VmRecordMapper extends JpaRepository<VmRecord, String> {
    @Modifying
    @Query("UPDATE VmRecord v set v.states = :states where v.vmId = :vmId")
    void updateNode(String states, String vmId);

    VmRecord getByVmId(String vmId);
}
