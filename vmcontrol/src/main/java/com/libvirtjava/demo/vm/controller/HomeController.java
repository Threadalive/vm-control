package com.libvirtjava.demo.vm.controller;

import com.libvirtjava.demo.vm.util.Const;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

//    @RequestMapping({"/","/index"})
//    public String index() {
//        return "/index";
//    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request) throws Exception {

        LOGGER.info("HomeController.login()");

        Map<String,Object> resultMap = new HashMap<>(1);

        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");

        LOGGER.info("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                LOGGER.info("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccount";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                LOGGER.info("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentials";
            } else if (DisabledAccountException.class.getName().equals(exception)){
                LOGGER.info("DisabledAccount");
                msg = "locked";
            }else if (null != request.getSession().getAttribute("currentUser")){
                msg = Const.SUCCEED;
            }else {
                msg = Const.FAIL;
                LOGGER.info("error -- >" + exception);
            }
        }
        resultMap.put(Const.MSG, msg);
        // 此方法不处理登录成功,由shiro进行处理
        return resultMap;
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "/403";
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return 未登录
     */
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public Map<String,Object> unauth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Const.MSG, "unLogin");
        return map;
    }


}
