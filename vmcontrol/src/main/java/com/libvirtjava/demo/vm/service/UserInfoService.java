package com.libvirtjava.demo.vm.service;

import com.libvirtjava.demo.vm.domain.user.UserInfo;
import com.libvirtjava.demo.vm.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 用户服务
 * @Author zhenxing.dong
 * @Date 2019/12/20 16:26
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    public UserInfo getUserInfo(String userName) {
        return userInfoMapper.getUserInfoByUsername(userName);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    public UserInfo getUserInfoById(Integer userId) {
        return userInfoMapper.getUserInfoByUid(userId);
    }


}
