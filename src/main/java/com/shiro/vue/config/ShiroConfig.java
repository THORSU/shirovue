package com.shiro.vue.config;


import com.shiro.vue.shiro.AuthRealm;
import com.shiro.vue.shiro.CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author: QuincySu
 * Date: 2018-10-10
 * Time: 16:47
 */
@Configuration
public class ShiroConfig {


    /**
     * 日志
     */
    private final static Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        log.info("====================== shrio拦截器工厂注入开始 =====================");

        ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        sffb.setSecurityManager(securityManager);
        // 登录页面
        sffb.setLoginUrl("/LoginApi/v1/login");
        // 登录成功后要跳转的链接
        // 未授权界面;
        sffb.setUnauthorizedUrl("/login");
        // 拦截器
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 可以匿名访问
        filterMap.put("/LoginApi/v1/login", "anon");
        filterMap.put("/LoginApi/v1/logout", "anon");
        filterMap.put("/UserApi/v1/uploadHander", "anon");
        // 需要认证才可以访问
        filterMap.put("/*", "authc");
        filterMap.put("/**", "authc");
        filterMap.put("/*.*", "authc");
        sffb.setFilterChainDefinitionMap(filterMap);

        log.info("====================== shrio拦截器工厂注入成功 =====================");
        return sffb;
    }

    //配置自定义的密码比较器
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

    /**
     * 配置核心安全事务管理
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        log.info("========================shiro已经加载======================");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm());
        return securityManager;
    }

    /**
     * 配置自定义的权限登录器
     *
     * @return
     */
    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(credentialsMatcher());
        return authRealm;
    }
}
