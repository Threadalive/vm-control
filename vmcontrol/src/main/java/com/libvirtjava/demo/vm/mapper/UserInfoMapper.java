package com.libvirtjava.demo.vm.mapper;

import com.libvirtjava.demo.vm.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/20 16:27
 */
public interface UserInfoMapper extends JpaRepository<UserInfo, Integer> {

    /**
     * 查询用户信息
     *
     * @param userName 用户名
     * @return 用户实体
     */
    UserInfo getUserInfoByUsername(String userName);

    UserInfo getUserInfoByUid(Integer userId);
}
