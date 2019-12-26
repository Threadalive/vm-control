package com.libvirtjava.demo.vm.util.config;

import com.alibaba.fastjson.JSONObject;
import com.libvirtjava.demo.vm.domain.user.UserInfo;
import com.libvirtjava.demo.vm.util.Const;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/25 21:24
 */
public class CustomerAuthenticatingFilter extends AuthenticatingFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAuthenticatingFilter.class);

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        LOGGER.info("--------executeLogin---------");
        CustomFormAuthenticationFilter formAuthen = new CustomFormAuthenticationFilter();
        AuthenticationToken token = formAuthen.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            LOGGER.info("*******" + msg);
            throw new IllegalStateException(msg);
        }
        try {
            LOGGER.info("----------进行核对信息----------------");

            Subject subject = getSubject(request, response);

            //代理给SecurityManager，通过前面定义的MyShiroRealm进行登录校验
            subject.login(token);

            return this.onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            LOGGER.info("-----onLoginFailure;---------");
            //进入登录失败的处理
            return this.onLoginFailure(token, e, request, response);
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        LOGGER.info("AuthenticatingFilterOverride--------onLoginSuccess------");
//        PrintWriter out = null;
//        JSONObject json = new JSONObject();
        UserInfo userInfo = (UserInfo) subject.getPrincipal();
        //获取session
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpSession session = httpServletRequest.getSession();
        //设置当前session的用户属性
        session.setAttribute("currentUser", userInfo);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

//        json.put("userId",userInfo.getUid());
//        out.println(json);
//        out.flush();
//        out.close();
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        LOGGER.info("CustomerAuthenticatingFilter--------onLoginFailure------");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            LOGGER.error("{}", e1);
        }
        //返回登陆失败的数据
        JSONObject json = new JSONObject();
        //错误名
        String excName = e.getClass().getName();
        if (excName.equals(UnknownAccountException.class.getName())) {
            json.put(Const.MSG, "noSuchUser");
        } else if (excName.equals(IncorrectCredentialsException.class.getName())) {
            LOGGER.info("=========");
            json.put(Const.MSG, "pwdWrong");
        } else if (excName.equals(DisabledAccountException.class.getName())) {
            LOGGER.info("用户锁定");
            json.put(Const.MSG, "userLocked");
        }else {
            json.put(Const.MSG,Const.FAIL);
        }
        out.println(json);
        out.flush();
        out.close();
        return false;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
