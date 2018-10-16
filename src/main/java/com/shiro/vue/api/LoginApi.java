package com.shiro.vue.api;

import com.shiro.vue.Enum.EnumCode;
import com.shiro.vue.service.IUserService;
import com.shiro.vue.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: QuincySu
 * Date: 2018-10-11
 * Time: 11:38
 */
@RestController
@RequestMapping(value = "LoginApi/v1")
public class LoginApi {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String name, String pass, HttpSession session, HttpServletRequest request) {
        return userService.login(name, pass, session, request);
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public Object logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return ResultUtil.result(EnumCode.OK.getValue(), "退出登陆成功");
        } catch (Exception e) {
            return "/login";
        }
    }
}
