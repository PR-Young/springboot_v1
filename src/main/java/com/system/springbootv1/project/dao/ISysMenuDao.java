package com.system.springbootv1.project.dao;

import com.system.springbootv1.project.model.SysMenu;

import java.util.List;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
public interface ISysMenuDao extends IBaseDao<SysMenu> {

    List<SysMenu> getMenuByUserId(String userId);

    SysMenu getByPId(String pid);

    List<String> getMenuByRole(String roleId);

    List<SysMenu> selectListByUser(SysMenu menu);

    int deleteById(String menuId);

    int hasChildByMenuId(String menuId);

}
