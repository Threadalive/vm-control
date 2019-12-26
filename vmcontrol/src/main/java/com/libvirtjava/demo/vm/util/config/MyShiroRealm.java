package com.libvirtjava.demo.vm.util.config;

import com.libvirtjava.demo.vm.domain.user.SysPermission;
import com.libvirtjava.demo.vm.domain.user.SysRole;
import com.libvirtjava.demo.vm.domain.user.UserInfo;
import com.libvirtjava.demo.vm.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/20 16:12
 */
public class MyShiroRealm extends AuthorizingRealm {

    /**
     * 用户登录次数计数 redisKey 前缀
     */
    private String SHIRO_LOGIN_COUNT = "shiro_login_count_";

    /**
     * 用户登录是否被锁定5分钟 redisKey 前缀
     */
    private String SHIRO_IS_LOCK = "shiro_is_lock_";

    /**
     * 最大尝试次数3
     */
    private Integer MAX_RETRY_COUNT = 3;

    private static Logger LOGGER = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    @Override
//    public String getName() {
//        return "myRealm";
//    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取传入的账号密码
        String userName = token.getPrincipal().toString();

        String pwd = new String((char[])token.getCredentials());

        ValueOperations<String,String> opsForValue = stringRedisTemplate.opsForValue();

        //尝试次数+1
        opsForValue.increment(SHIRO_LOGIN_COUNT+userName,1);

        //判断次数
        if(Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+userName)) >= MAX_RETRY_COUNT){
            //锁定，5分钟后过期
            opsForValue.set(SHIRO_IS_LOCK+userName, "LOCK");
            stringRedisTemplate.expire(SHIRO_IS_LOCK+userName, 5, TimeUnit.MINUTES);
            stringRedisTemplate.expire(SHIRO_LOGIN_COUNT+userName, 5, TimeUnit.MINUTES);
        }
        if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+userName))){
            throw new DisabledAccountException("由于密码输入错误次数大于3次，5分钟后再次尝试！");
        }

        UserInfo userInfo = userInfoService.getUserInfo(userName);

        if(userInfo == null){
            return null;
        }else if("2".equals(userInfo.getState())){
            /**
             * 如果用户的status为禁用。那么就抛出DisabledAccountException
             */
            throw new DisabledAccountException("此帐号已经设置为禁止登录！");
        }
        if (!userName.equals(userInfo.getUsername())) {
            //如果用户名错误
            throw new UnknownAccountException();
        }else if (!pwd.equals(userInfo.getPassword())) {
            //如果密码错误
            LOGGER.info(pwd);
            throw new IncorrectCredentialsException();
        }else {
            //清除登录次数记录
            opsForValue.set(SHIRO_LOGIN_COUNT+userName, "0");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(),
//                ByteSource.Util.bytes(userInfo.getSalt()),
                getName()
        );

        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return authenticationInfo;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        LOGGER.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        //权限信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        UserInfo userInfo  = null;
        try {
            String userName = (String) principals.getPrimaryPrincipal();
            userInfo = userInfoService.getUserInfo(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(SysRole role:userInfo.getRoleList()){
            //添加角色信息
            authorizationInfo.addRole(role.getRole());
            LOGGER.info(role.getRole());
            for(SysPermission p:role.getPermissions()){
                //添加权限信息
                authorizationInfo.addStringPermission(p.getPermission());
                LOGGER.info(p.getPermission());
            }
        }
            return authorizationInfo;
    }
}
