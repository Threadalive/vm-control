package com.libvirtjava.demo.vm.util;

import com.libvirtjava.demo.vm.domain.UserInfo;
import com.libvirtjava.demo.vm.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/20 16:12
 */
public class MyShiroRealm implements Realm {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public String getName() {
        return "myRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取传入的账号密码
        String userName = token.getPrincipal().toString();

        String pwd = new String((char[]) token.getCredentials());

        UserInfo userInfo = userInfoService.getUserInfo(userName);

        if(userInfo == null){
            return null;
        }
        if (!userInfo.getUsername().equals(userName)) {
            //如果用户名错误
            throw new UnknownAccountException();
        }
        if (!userInfo.getPassword().equals(pwd)) {
            //如果密码错误
            System.out.println(pwd);
            throw new IncorrectCredentialsException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getUsername(),
                userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getSalt()),
                getName()
        );

        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return authenticationInfo;
    }
}
