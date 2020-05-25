package com.system.springbootv1.common.jwt;

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
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
