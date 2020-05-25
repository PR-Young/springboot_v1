package com.system.springbootv1.project.dao;

import com.system.springbootv1.project.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: yy 2020/01/22
 **/
public interface ISysUserDao extends IBaseDao<SysUser> {

    SysUser getUserByName(String userName);

    Integer checkAccount(String account);

    int resetPassword(@Param("password") String password, @Param("id") String id);

    List<SysUser> getUserByRole(String roleId);

    List<SysUser> allUser(String roleId);

}
