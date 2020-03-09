package com.system.springbootv1.dao;

import com.system.springbootv1.model.SysRole;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/25
 **/
public interface ISysRoleDao extends BaseDao<SysRole> {
    List<SysRole> getRoleByUserId(String userId);

    List<String> getPermsByRoleId(String roleId);

    int insertRoleUser(Map<String, Object> map);

    int insertPermission(Map<String, Object> map);

    int deleteRoleUser(String roleId);

    int deletePermission(String roleId);
}
