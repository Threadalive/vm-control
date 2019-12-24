package com.libvirtjava.demo.vm.controller;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description TODO
 * @Author zhenxing.dong
 * @Date 2019/12/20 17:21
 */
@Controller
public class HomeController {

    /**
     * 日志记录
     */
    private Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping({"/","/index"})
    public String index() {
        return "/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {

        LOGGER.info("HomeController.login()");

        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");

        LOGGER.info("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                LOGGER.info("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                LOGGER.info("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if (DisabledAccountException.class.getName().equals(exception)){
                LOGGER.info("DisabledAccountException");

                msg = "5分钟后再来吧";
            }else{
                msg = "else >> " + exception;
                LOGGER.info("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "/403";
    }


}
