package com.system.springbootv1.common.shiro;

import com.system.springbootv1.common.exception.UserException;
import com.system.springbootv1.project.model.SysUser;
import com.system.springbootv1.utils.BeanUtils;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * @description:
 * @author: yy 2020/01/22
 **/
public class ShiroUtils {
    private ShiroUtils() {
    }

    /**
     * 获取shiro subject
     */
    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取登录session
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 退出登录
     */
    public static void logout() {
        getSubjct().logout();
    }

    /**
     * 获取登录用户model
     */
    public static SysUser getUser() throws Exception {
        SysUser user = null;
        Object obj = getSubjct().getPrincipal();
        if (StringUtils.isNotNull(obj)) {
            user = new SysUser();
            BeanUtils.copyBeanProp(user, obj);
        } else {
            throw new UserException();
        }
        return user;
    }

    /**
     * set用户
     */
    public static void setUser(SysUser user) {
        Subject subject = getSubjct();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    /**
     * 清除授权信息
     */
    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm = (MyShiroRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    /**
     * 获取登录用户id
     */
    public static String getUserId() throws Exception {
        SysUser user = getUser();
        if (user == null || user.getId() == null) {
            throw new RuntimeException("用户不存在！");
        }
        return user.getId().trim();
    }

    /**
     * 获取登录用户name
     */
    public static String getLoginName() throws Exception {
        SysUser user = getUser();
        if (user == null) {
            throw new RuntimeException("用户不存在！");
        }
        return user.getUserName();
    }

    /**
     * 获取登录用户ip
     */
    public static String getIp() {
        return getSubjct().getSession().getHost();
    }

    /**
     * 获取登录用户sessionid
     */
    public static String getSessionId() {
        return String.valueOf(getSubjct().getSession().getId());
    }
}
