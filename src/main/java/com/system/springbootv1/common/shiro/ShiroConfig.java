package com.system.springbootv1.common.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.system.springbootv1.common.config.BaseConfig;
import com.system.springbootv1.common.redis.RedisSessionDao;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/22
 **/
@Configuration
public class ShiroConfig {

    @Resource
    BaseConfig baseConfig;


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setLoginUrl("/login/login");
        shiroFilterFactoryBean.setSuccessUrl("/sys/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/noAuth");
        Map<String, String> filterMap = new HashMap<String, String>();
        filterMap.put("/static/**", "anon");
        filterMap.put("/login/login", "anon");
        filterMap.put("/logout", "logout");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/actuator/**", "anon");
        filterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(Realm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setRealm(shiroRealm);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(baseConfig.getTimeout() * 1000);
        defaultWebSessionManager.setSessionDAO(sessionDao());
        defaultWebSessionManager.setCacheManager(redisCacheManager());
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        defaultWebSessionManager.setSessionIdCookie(sessionIdCookie());
        return defaultWebSessionManager;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");//采用MD5 进行加密
        hashedCredentialsMatcher.setHashIterations(1);//加密次数
        return hashedCredentialsMatcher;
    }

    //设置cookie
    @Bean
    public Cookie sessionIdCookie() {
        Cookie sessionIdCookie = new SimpleCookie("ARES_COOKIE");
        sessionIdCookie.setMaxAge(-1);
        sessionIdCookie.setHttpOnly(true);
        return sessionIdCookie;
    }

//    @Bean
//    public CacheManager cacheManager() {
//        MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();//使用内存缓存
//        return cacheManager;
//    }

    @Bean
    public ShiroCacheManager redisCacheManager() {
        return new ShiroCacheManager(baseConfig.getTimeout());
    }

    @Bean
    public RedisSessionDao sessionDao() {
        return new RedisSessionDao();
    }

    @Bean
    public AuthorizingRealm shiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        MyShiroRealm shiroRealm = new MyShiroRealm();
        //校验密码用到的算法
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return shiroRealm;
    }

    /**
     * 启用shiro注解
     * 加入注解的使用，不加入这个注解不生效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
