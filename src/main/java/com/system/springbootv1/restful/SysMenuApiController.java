package com.system.springbootv1.restful;

import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysMenu;
import com.system.springbootv1.project.service.SysMenuService;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/05/05
 **/
@RestController
@RequestMapping("/system/menu/*")
public class SysMenuApiController {
    @Resource
    SysMenuService menuService;

    @RequiresPermissions("menu:list")
    @RequestMapping("list")
    public Object list(SysMenu menu) throws Exception {
        String userId = ShiroUtils.getUserId();
        List<SysMenu> menuList = menuService.selectMenuList(menu, userId);
        return BaseResult.successData(menuList);
    }

    @GetMapping(value = "{menuId}")
    public Object getInfo(@PathVariable String menuId) {
        return BaseResult.successData(menuService.getById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("treeselect")
    public Object treeselect(SysMenu menu) throws Exception {
        String userId = ShiroUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return BaseResult.successData(menuService.buildMenuTreeSelect(menus));
    }

    @RequiresPermissions("menu:edit")
    @PostMapping("edit")
    public Object edit(@Validated @RequestBody SysMenu menu) throws Exception {
        if (StringUtils.isEmpty(menu.getId())) {
            menu.setCreator(ShiroUtils.getUserId());
            menuService.insert(menu);
        } else {
            menu.setModifier(ShiroUtils.getUserId());
            menuService.update(menu);
        }
        return BaseResult.success();
    }

    @RequiresPermissions("menu:delete")
    @DeleteMapping("{menuId}")
    public Object remove(@PathVariable String menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return BaseResult.error("存在子菜单,不允许删除");
        }
        menuService.remove(menuId);
        return BaseResult.success();
    }

    @GetMapping(value = "roleMenuTreeselect/{roleId}")
    public Object roleMenuTreeselect(@PathVariable("roleId") String roleId) throws Exception {
        String userId = ShiroUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(new SysMenu(), userId);
        BaseResult result = BaseResult.success();
        result.put("checkedKeys", menuService.selectMenuByRole(roleId));
        result.put("menus", menuService.buildMenuTreeSelect(menus));
        return result;
    }
}
