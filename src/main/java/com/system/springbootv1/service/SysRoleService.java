package com.system.springbootv1.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.springbootv1.dao.ISysRoleDao;
import com.system.springbootv1.model.SysRole;
import com.system.springbootv1.utils.SnowflakeIdWorker;
import com.system.springbootv1.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/26
 **/
@Service
public class SysRoleService implements BaseService<SysRole> {
    @Resource
    ISysRoleDao sysRoleDao;

    public PageInfo<SysRole> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysRole> userList = sysRoleDao.list(map);
        PageInfo<SysRole> userPageInfo = new PageInfo<>(userList);
        return userPageInfo;
    }

    @Override
    public void insert(SysRole obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysRoleDao.insert(obj);
    }

    @Override
    public void update(SysRole obj) {
        obj.setModifyTime(new Date());
        sysRoleDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysRoleDao.deleteByIds(ids);
    }

    @Override
    public SysRole getById(String id) {
        return sysRoleDao.getById(id);
    }

    public void saveAssign(String users, String menus, String roleId) {
        sysRoleDao.deleteRoleUser(roleId);
        if (StringUtils.isNotEmpty(users)) {
            String[] userIds = users.split(",");
            for (String id : userIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", SnowflakeIdWorker.getUUID());
                map.put("userId", id);
                map.put("roleId", roleId);
                sysRoleDao.insertRoleUser(map);
            }
        }
        sysRoleDao.deletePermission(roleId);
        if (StringUtils.isNotEmpty(menus)) {
            String[] menuIds = menus.split(",");
            for (String id : menuIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", SnowflakeIdWorker.getUUID());
                map.put("menuId", id);
                map.put("roleId", roleId);
                sysRoleDao.insertPermission(map);
            }
        }
    }
}
