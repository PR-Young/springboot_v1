package com.system.springbootv1.project.controller;

import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysRole;
import com.system.springbootv1.project.model.TableResult;
import com.system.springbootv1.project.service.SysMenuService;
import com.system.springbootv1.project.service.SysRoleService;
import com.system.springbootv1.project.service.SysUserService;
import com.system.springbootv1.utils.ServletUtils;
import com.system.springbootv1.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @description:
 * @author: yy 2020/01/26
 **/
@Api("角色管理")
@Controller
@RequestMapping("/role/*")
public class SysRoleController {

    @Resource
    SysRoleService sysRoleService;
    @Resource
    SysUserService sysUserService;
    @Resource
    SysMenuService sysMenuService;

    private String prefix = "role";



    @ApiOperation(value = "角色列表")
    @RequestMapping("list")
    @RequiresPermissions("role:list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response) {
        PageInfo<SysRole> userPageInfo = sysRoleService.list(ServletUtils.getParameterToInt("pageNum"), ServletUtils.getParameterToInt("pageSize"), ServletUtils.getQueryParams());
        TableResult<SysRole> tableResult = new TableResult<>(userPageInfo.getPageNum(), userPageInfo.getTotal(), userPageInfo.getList());
        return tableResult;
    }

    @ApiOperation(value = "跳转新增页面")
    @GetMapping("add")
    public String add(ModelMap map) {
        map.put("sysRole", new SysRole());
        return prefix + "/edit";
    }

    @ApiOperation(value = "跳转编辑页面")
    @GetMapping("update/{roleId}")
    public String edit(@PathVariable("roleId") String id, ModelMap map) {
        map.put("sysRole", sysRoleService.getById(id));
        return prefix + "/edit";
    }

    @ApiOperation(value = "新增/修改保存")
    @RequestMapping("edit")
    @RequiresPermissions("role:edit")
    @ResponseBody
    public Object edit(SysRole sysRole, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(sysRole.getId())) {
            sysRoleService.insert(sysRole);
        } else {
            sysRoleService.update(sysRole);
        }
        return BaseResult.success();
    }

    @ApiOperation(value = "删除操作")
    @RequestMapping("delete")
    @RequiresPermissions("role:delete")
    @ResponseBody
    public Object delete(HttpServletRequest request, HttpServletResponse response) {
        String ids = ServletUtils.getParameter("ids");
        if (null != ids) {
            sysRoleService.deleteByIds(Arrays.asList(ids.split(",")));
        }
        return BaseResult.success();
    }

    @ApiOperation(value = "跳转分配权限页面")
    @GetMapping("assign/{roleId}")
    public String assign(@PathVariable("roleId") String id, ModelMap map) {
        map.put("roleId", id);
        return prefix + "/assign";
    }

    @ApiOperation(value = "保存权限")
    @RequestMapping("saveAssign")
    @ResponseBody
    public Object saveAssign(HttpServletRequest request, HttpServletResponse response) {
        String userIds = ServletUtils.getParameter("users");
        String menuIds = ServletUtils.getParameter("menus");
        String roleId = ServletUtils.getParameter("roleId");
        sysRoleService.saveAssign(userIds, menuIds, roleId);
        return BaseResult.success();
    }

    @ApiOperation(value = "获取所有用户")
    @RequestMapping("allUser/{roleId}")
    @ResponseBody
    public Object allUser(@PathVariable("roleId") String id, HttpServletRequest request, HttpServletResponse response) {
        return BaseResult.successData(200, sysUserService.assignAllUser(id));
    }

    @ApiOperation(value = "获取已分配用户")
    @RequestMapping("selectedUser/{roleId}")
    @ResponseBody
    public Object selectedUser(@PathVariable("roleId") String id, HttpServletRequest request, HttpServletResponse response) {
        return BaseResult.successData(200, sysUserService.getUserByRole(id));
    }

    @ApiOperation(value = "获取菜单")
    @RequestMapping("tree/{roleId}")
    @ResponseBody
    public Object tree(@PathVariable("roleId") String id, HttpServletRequest request, HttpServletResponse response) {
        return BaseResult.successData(200, sysMenuService.getBootstrapTreeByRole(id));
    }
}
