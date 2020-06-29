package com.system.springbootv1.common.jwt;

import com.system.springbootv1.common.spring.SpringUtils;
import com.system.springbootv1.project.model.SysUser;
import com.system.springbootv1.project.service.SysUserService;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description:
 * @author: yy 2020/05/09
 **/
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        String userName = JwtUtil.getUsername(token);
        SysUserService userService = SpringUtils.getBean(SysUserService.class);
        SysUser user = userService.getUserByName(userName);
        return user;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
