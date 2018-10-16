package com.shiro.vue.shiro;

import com.shiro.vue.pojo.dto.UserInfoDto;
import com.shiro.vue.pojo.entity.User;
import com.shiro.vue.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author: QuincySu
 * Date: 2018-10-10
 * Time: 17:28
 */
public class AuthRealm extends AuthorizingRealm {

    /**
     * 日志打印
     */
    private final static Logger log = LoggerFactory.getLogger(AuthRealm.class);

    @Autowired
    public IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * @desc: 授权
     * @date: 2017/12/18
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("======================= 认证登陆 ======================");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String name = usernamePasswordToken.getUsername();
        String pass = String.valueOf(usernamePasswordToken.getPassword());
        List<UserInfoDto> list = userService.checkUser(name, pass);
        UserInfoDto userInfoDto = null;
        if (null == list || list.isEmpty()) {
            throw new AuthenticationException("账号或密码错误");
        } else if (list.get(0).getState() == 0) {
            /**
             * 账号被禁用
             */
            throw new AuthenticationException("账号已被禁止登陆");
        } else {
            userInfoDto = list.get(0);
            //登录成功
            //更新登录时间 last login time
            User user = new User();
            user.setId(userInfoDto.getId());
            user.setLastLoginTime(new Date());
            userService.updateById(user);
        }
        log.info("======================= 登陆成功 ======================");
        return new SimpleAuthenticationInfo(userInfoDto, userInfoDto.getPassword(), getName());
    }
}
