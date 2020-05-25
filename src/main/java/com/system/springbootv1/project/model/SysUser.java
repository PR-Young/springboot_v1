package com.system.springbootv1.project.model;

import com.system.springbootv1.common.model.BaseModel;

import java.io.Serializable;

/**
 * @description:
 * @author: yy 2020/01/22
 **/
public class SysUser extends BaseModel implements Serializable {

    private String account;
    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private String avatar;
    private String[] roleIds;
    private boolean isAdmin;

    public SysUser() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public boolean isAdmin() {
        return isAdmin(getId());
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public static boolean isAdmin(String userId) {
        return userId != null && "1".equals(userId);
    }
}
