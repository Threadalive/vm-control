package com.libvirtjava.demo.vm.util.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/24 14:11
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("进入拒绝访问接口");
        //是否为登陆URL
        if (this.isLoginRequest(request, response)) {
            LOGGER.info("--------------isLoginRequest--------------");
            if (this.isLoginSubmission(request, response)) {
                LOGGER.info("--------------isLoginSubmission--------------");
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Login submission detected.  Attempting to execute login.");
                }
                CustomerAuthenticatingFilter filter = new CustomerAuthenticatingFilter();
                return filter.executeLogin(request, response);
            } else {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Login page view.");
                }
                return true;
            }
        } else {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            }
            //返回json数据
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("no-session", "unLogin");
            out.println(json);
            out.flush();
            out.close();
            return false;
        }
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        //设置账户密码的token
        String username = getUsername(request);
        String password = getPassword(request);
        return createToken(username, password, request, response);
    }
}
