package com.system.springbootv1.common.shiro;

import com.alibaba.fastjson.JSON;
import com.system.springbootv1.dao.ISysRoleDao;
import com.system.springbootv1.dao.ISysUserDao;
import com.system.springbootv1.model.SysRole;
import com.system.springbootv1.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/01/22
 **/
@Component
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    ISysUserDao sysUserDao;
    @Resource
    ISysRoleDao sysRoleDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Object object = principalCollection.getPrimaryPrincipal();
        SysUser user = new SysUser();
        if (object instanceof SysUser) {
            user = (SysUser) object;
        } else {
            user = JSON.parseObject(JSON.toJSON(object).toString(), SysUser.class);
        }
        List<SysRole> roleList = sysRoleDao.getRoleByUserId(user.getId());
        for (SysRole role : roleList) {
            authorizationInfo.addRole(role.getRoleName());
            List<String> perms = new ArrayList<>();
            if ("gly".equalsIgnoreCase(role.getRoleName())) {
                perms = sysRoleDao.getPermsByRoleId(null);
            } else {
                perms = sysRoleDao.getPermsByRoleId(role.getId());
            }
            for (String perm : perms) {
                authorizationInfo.addStringPermission(perm);
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (null == authenticationToken.getPrincipal()) {
            return null;
        }
        String userName = (String) authenticationToken.getPrincipal();
        SysUser user = sysUserDao.getUserByName(userName);
        if (null == user) {
            return null;
        } else {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            return authenticationInfo;
        }

    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
