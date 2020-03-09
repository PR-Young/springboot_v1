package com.system.springbootv1.service;

import com.system.springbootv1.dao.ISysMenuDao;
import com.system.springbootv1.model.BootstrapTree;
import com.system.springbootv1.model.SysMenu;
import com.system.springbootv1.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
@Service
public class SysMenuService implements BaseService<SysMenu> {

    @Resource
    ISysMenuDao sysMenuDao;

    /**
     * 获取转换成bootstarp的权限数据
     *
     * @return
     */
    public BootstrapTree getBootstrapTree(String userId) {
        List<BootstrapTree> treeList = new ArrayList<BootstrapTree>();
        List<SysMenu> menuList = getAll(userId);
        treeList = getBootstrapTree(menuList, "0");
        return new BootstrapTree("菜单", "fa fa-home", "", "0", "###", 0, treeList, 0);
    }

    /**
     * 获取树
     *
     * @param menuList
     * @param parentId
     * @return
     */
    private static List<BootstrapTree> getBootstrapTree(List<SysMenu> menuList, String parentId) {
        List<BootstrapTree> treeList = new ArrayList<>();
        List<BootstrapTree> childList = null;
        for (SysMenu sysMenu : menuList) {
            if (2 != sysMenu.getType()) {
                sysMenu.setPId(sysMenu.getPId() == null || sysMenu.getPId().trim().equals("") ? "0" : sysMenu.getPId());
                if (sysMenu.getPId().trim().equals(parentId)) {
                    if (sysMenu.getChildCount() != null && sysMenu.getChildCount() > 0) {
                        childList = getBootstrapTree(menuList, String.valueOf(sysMenu.getId()));
                    }
                    BootstrapTree bootstrapTree = new BootstrapTree(sysMenu.getName(), sysMenu.getIcon(), "", String.valueOf(sysMenu.getId()), sysMenu.getUrl(), sysMenu.getIsBlank(), childList, sysMenu.getVisible());
                    treeList.add(bootstrapTree);
                    childList = null;
                }
            }
        }
        return treeList.size() > 0 ? treeList : null;
    }

    /**
     * 根据用户id获取用户角色如果用户为null 获取所有权限
     *
     * @return
     */
    public List<SysMenu> getAll(String userId) {
        return sysMenuDao.getMenuByUserId(userId);
    }

    public List<SysMenu> list(Map<String, Object> map) {
        return sysMenuDao.list(map);
    }

    @Override
    public void insert(SysMenu obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysMenuDao.insert(obj);
    }

    @Override
    public void update(SysMenu obj) {
        obj.setModifyTime(new Date());
        sysMenuDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysMenuDao.deleteByIds(ids);
    }

    @Override
    public SysMenu getById(String id) {
        return sysMenuDao.getById(id);
    }

    public SysMenu getByPId(String pid) {
        return sysMenuDao.getByPId(pid);
    }

    public BootstrapTree getBootstrapTreeByRole(String roleId) {
        List<BootstrapTree> treeList = new ArrayList<BootstrapTree>();
        List<SysMenu> menuList = getAll(null);
        List<String> roleMenu = sysMenuDao.getMenuByRole(roleId);
        treeList = getBootstrapTree(menuList, "0", roleMenu);
        return new BootstrapTree("菜单", "fa fa-home", "", "0", "###", 0, new HashMap<>(), treeList, 0);
    }

    /**
     * 获取树
     *
     * @param menuList
     * @param parentId
     * @return
     */
    private static List<BootstrapTree> getBootstrapTree(List<SysMenu> menuList, String parentId, List<String> roleMenu) {
        List<BootstrapTree> treeList = new ArrayList<>();
        List<BootstrapTree> childList = null;
        for (SysMenu sysMenu : menuList) {
            Map<String, Object> state = new HashMap<>();
            sysMenu.setPId(sysMenu.getPId() == null || sysMenu.getPId().trim().equals("") ? "0" : sysMenu.getPId());
            if (sysMenu.getPId().trim().equals(parentId)) {
                if (sysMenu.getChildCount() != null && sysMenu.getChildCount() > 0) {
                    childList = getBootstrapTree(menuList, String.valueOf(sysMenu.getId()), roleMenu);
                }
                if (roleMenu.contains(sysMenu.getId())) {
                    state.put("checked", true);
                }
                BootstrapTree bootstrapTree = new BootstrapTree(sysMenu.getName(), sysMenu.getIcon(), "", String.valueOf(sysMenu.getId()), sysMenu.getUrl(), sysMenu.getIsBlank(), state, childList, sysMenu.getVisible());
                treeList.add(bootstrapTree);
                childList = null;
            }
        }
        return treeList.size() > 0 ? treeList : null;
    }
}
