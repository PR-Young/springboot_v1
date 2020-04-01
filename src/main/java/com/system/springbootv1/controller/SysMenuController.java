package com.system.springbootv1.controller;

import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.model.BaseResult;
import com.system.springbootv1.model.SysMenu;
import com.system.springbootv1.model.SysUser;
import com.system.springbootv1.service.SysMenuService;
import com.system.springbootv1.utils.ServletUtils;
import com.system.springbootv1.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
@Api("菜单管理")
@Controller
@RequestMapping("/menu/*")
public class SysMenuController extends BaseController {

    @Resource
    SysMenuService sysMenuService;

    private String prefix = "menu";

    /**
     * 获取所有的转换成bootstarp的权限数据
     *
     * @return
     */
    @ApiOperation(value = "菜单树")
    @RequestMapping("getMenuTree")
    @ResponseBody
    public Object getBootstrapTree() {
        SysUser user = ShiroUtils.getUser();
        return BaseResult.successData(200, sysMenuService.getBootstrapTree(user.getAccount()));
    }

    @ApiOperation(value = "菜单列表")
    @PostMapping("list")
    @RequiresPermissions("menu:list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = ServletUtils.getQueryParams();
        List<SysMenu> menuList = sysMenuService.list(map);
        return menuList;
    }

    @ApiOperation(value = "跳转新增页面")
    @GetMapping("add")
    public String add(ModelMap map) {
        map.put("sysMenu", new SysMenu());
        map.put("sysParentMenu", new SysMenu());
        return prefix + "/edit";
    }

    @ApiOperation(value = "跳转编辑页面")
    @GetMapping("update/{menuId}")
    public String edit(@PathVariable("menuId") String id, ModelMap map) {
        SysMenu sysMenu = sysMenuService.getById(id);
        SysMenu parentMenu = sysMenuService.getById(sysMenu.getPId());
        if (null == parentMenu) {
            parentMenu = new SysMenu();
            parentMenu.setId("0");
            parentMenu.setName("菜单");
        }
        map.put("sysMenu", sysMenu);
        map.put("sysParentMenu", parentMenu);
        return prefix + "/edit";
    }

    @ApiOperation(value = "新增/修改保存")
    @RequestMapping("edit")
    @RequiresPermissions("menu:edit")
    @ResponseBody
    public Object edit(@Valid SysMenu sysMenu, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(sysMenu.getId())) {
            sysMenuService.insert(sysMenu);
        } else {
            sysMenuService.update(sysMenu);
        }
        return BaseResult.success();
    }

    @ApiOperation(value = "删除操作")
    @RequestMapping("delete")
    @RequiresPermissions("menu:delete")
    @ResponseBody
    public Object delete(HttpServletRequest request, HttpServletResponse response) {
        String ids = ServletUtils.getParameter("ids");
        if (null != ids) {
            sysMenuService.deleteByIds(Arrays.asList(ids.split(",")));
        }
        return BaseResult.success();
    }

    /**
     * 跳转到菜单树页面
     *
     * @return
     */
    @ApiOperation(value = "跳转到菜单树页面")
    @GetMapping("tree")
    public String Tree() {
        return prefix + "/tree";
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @ApiOperation(value = "获取菜单树")
    @PostMapping("tree/{pid}")
    @ResponseBody
    public Object Tree(@PathVariable("pid") String pid) {
        return BaseResult.successData(200, sysMenuService.getBootstrapTree(null));
    }
}
