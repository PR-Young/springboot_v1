package com.system.springbootv1.project.controller;

import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysUser;
import com.system.springbootv1.project.model.TableResult;
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
 * @author: yy 2020/01/24
 **/
@Api("用户管理")
@Controller
@RequestMapping("/user/*")
public class SysUserController {

    @Resource
    SysUserService sysUserService;

    private String prefix = "user";


    @ApiOperation(value = "用户列表")
    @RequestMapping("list")
    @RequiresPermissions("user:list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response) {
        PageInfo<SysUser> userPageInfo = sysUserService.list(ServletUtils.getParameterToInt("pageNum"), ServletUtils.getParameterToInt("pageSize"), ServletUtils.getQueryParams());
        TableResult<SysUser> tableResult = new TableResult<>(userPageInfo.getPageNum(), userPageInfo.getTotal(), userPageInfo.getList());
        return tableResult;
    }

    @ApiOperation(value = "跳转新增页面")
    @GetMapping("add")
    public String add(ModelMap map) {
        map.put("sysUser", new SysUser());
        return prefix + "/edit";
    }

    @ApiOperation(value = "跳转编辑页面")
    @GetMapping("update/{userId}")
    public String edit(@PathVariable("userId") String id, ModelMap map) {
        map.put("sysUser", sysUserService.getById(id));
        return prefix + "/edit";
    }

    @ApiOperation(value = "新增/修改保存")
    @RequestMapping("edit")
    @RequiresPermissions("user:edit")
    @ResponseBody
    public Object edit(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(sysUser.getId())) {
            sysUserService.insert(sysUser);
        } else {
            sysUserService.update(sysUser);
        }
        return BaseResult.success();
    }

    @ApiOperation(value = "删除操作")
    @RequestMapping("delete")
    @RequiresPermissions("user:delete")
    @ResponseBody
    public Object delete(HttpServletRequest request, HttpServletResponse response) {
        String ids = ServletUtils.getParameter("ids");
        if (null != ids) {
            sysUserService.deleteByIds(Arrays.asList(ids.split(",")));
        }
        return BaseResult.success();
    }

    @ApiOperation(value = "校验用户名")
    @RequestMapping("checkAccount")
    @ResponseBody
    public int checkAccount(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        return sysUserService.checkAccount(sysUser.getAccount());
    }

    @ApiOperation(value = "重置密码")
    @RequestMapping("resetPassword")
    @ResponseBody
    public Object resetPassword(HttpServletRequest request, HttpServletResponse response) {
        String id = ServletUtils.getParameter("id");
        sysUserService.resetPassword(id);
        return BaseResult.success();
    }
}
