package com.system.springbootv1.project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.dao.ISysRoleDao;
import com.system.springbootv1.project.dao.ISysUserDao;
import com.system.springbootv1.project.model.SysRole;
import com.system.springbootv1.project.model.SysUser;
import com.system.springbootv1.utils.MD5Util;
import com.system.springbootv1.utils.SnowflakeIdWorker;
import com.system.springbootv1.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/22
 **/
@Service
public class SysUserService implements BaseService<SysUser> {

    @Resource
    ISysUserDao sysUserDao;
    @Resource
    ISysRoleDao roleDao;

    public void insert(SysUser sysUser) {
        sysUser.setId(SnowflakeIdWorker.getUUID());
        sysUser.setPassword(MD5Util.encode("123456"));
        sysUser.setCreateTime(new Date());
        sysUserDao.insert(sysUser);
    }

    public PageInfo<SysUser> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysUser> userList = sysUserDao.list(map);
        PageInfo<SysUser> userPageInfo = new PageInfo<>(userList);
        return userPageInfo;
    }

    public List<SysUser> assignAllUser(String roleId) {
        List<SysUser> userList = sysUserDao.allUser(roleId);
        return userList;
    }

    public List<SysUser> getUserByRole(String roleId) {
        List<SysUser> userList = sysUserDao.getUserByRole(roleId);
        return userList;
    }

    @Override
    public void update(SysUser obj) {
        obj.setModifyTime(new Date());
        sysUserDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysUserDao.deleteByIds(ids);
    }

    @Override
    public SysUser getById(String id) {
        return sysUserDao.getById(id);
    }

    public int checkAccount(String account) {
        Integer num = sysUserDao.checkAccount(account);
        return num == null ? 0 : num;
    }

    public int resetPassword(String id) {
        return sysUserDao.resetPassword(MD5Util.encode("123456"), id);
    }

    public List<SysUser> selectUserList(SysUser user) {
        return sysUserDao.selectList(user);
    }

    public String insertUser(SysUser sysUser) {
        String id = SnowflakeIdWorker.getUUID();
        sysUser.setId(id);
        sysUser.setPassword(MD5Util.encode("123456"));
        sysUser.setCreateTime(new Date());
        sysUserDao.insert(sysUser);
        return id;
    }

    public String selectUserRoleGroup(String userId) {
        List<SysRole> roleList = roleDao.getRoleByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : roleList) {
            idsStr.append(role.getDescription()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    public int updatePassword(SysUser user,String passWord) {
        user.setPassword(MD5Util.encode(passWord));
        return sysUserDao.update(user);
    }

    public SysUser getUserByName(String userName){
        return sysUserDao.getUserByName(userName);
    }
}
