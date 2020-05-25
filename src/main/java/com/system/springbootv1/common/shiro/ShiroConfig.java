package com.system.springbootv1.common.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.system.springbootv1.common.config.BaseConfig;
import com.system.springbootv1.common.jwt.JwtFilter;
import com.system.springbootv1.common.redis.RedisSessionDao;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import javax.servlet.Filter;
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


    @Bean("shiroFilterFactory")
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, String> filterMap = new HashMap<>();
        if (baseConfig.isSeparate()) {
            shiroFilterFactoryBean.setLoginUrl("/unAuth");
            shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
            Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
//            filters.put("authc", new CustomFormAuthenticationFilter());
//            filters.put("perms", new CustomPermissionsAuthorizationFilter());
            filters.put("jwt", new JwtFilter());
            filterMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            filterMap.put("/login", "anon");
        } else {
            shiroFilterFactoryBean.setLoginUrl("/login/login");
            shiroFilterFactoryBean.setSuccessUrl("/sys/index");
            shiroFilterFactoryBean.setUnauthorizedUrl("/sys/noAuth");
        }
        filterMap.put("/static/**", "anon");
        filterMap.put("/login/login", "anon");
        filterMap.put("/logout", "logout");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/actuator/**", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/**", baseConfig.isSeparate() ? "jwt" : "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(MyShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        if (baseConfig.isSeparate()) {
            DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
            DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
            evaluator.setSessionStorageEnabled(false);
            subjectDAO.setSessionStorageEvaluator(evaluator);
            securityManager.setSubjectDAO(subjectDAO);
        } else {
            securityManager.setSessionManager(sessionManager());
        }
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }


    @Bean
    public DefaultWebSessionManager sessionManager() {
        if (baseConfig.isSeparate()) {
            MySessionManager sessionManager = new MySessionManager();
            sessionManager.setGlobalSessionTimeout(baseConfig.getTimeout() * 1000);
            sessionManager.setSessionDAO(sessionDao());
            sessionManager.setCacheManager(redisCacheManager());
            sessionManager.setDeleteInvalidSessions(true);
            sessionManager.setSessionIdCookieEnabled(true);
            sessionManager.setSessionIdCookie(sessionIdCookie());
            return sessionManager;
        } else {
            DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
            defaultWebSessionManager.setGlobalSessionTimeout(baseConfig.getTimeout() * 1000);
            defaultWebSessionManager.setSessionDAO(sessionDao());
            defaultWebSessionManager.setCacheManager(redisCacheManager());
            defaultWebSessionManager.setDeleteInvalidSessions(true);
            defaultWebSessionManager.setSessionIdCookieEnabled(true);
            defaultWebSessionManager.setSessionIdCookie(sessionIdCookie());
            return defaultWebSessionManager;
        }
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
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制指定注解的底层实现使用 cglib 方案
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
