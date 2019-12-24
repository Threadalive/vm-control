package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.menu.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @param
 * @return
 * @author zhenxing.dong
 */
public interface ClusterMapper extends JpaRepository<Cluster,Integer> {

    List<Cluster> findByStatus(int status);
}
