package com.system.springbootv1.dao;

import com.system.springbootv1.model.SysMenu;

import java.util.List;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
public interface ISysMenuDao extends IBaseDao<SysMenu> {

    List<SysMenu> getMenuByUserId(String userId);

    SysMenu getByPId(String pid);

    List<String> getMenuByRole(String roleId);
}
