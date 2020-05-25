package com.system.springbootv1.restful;

import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.project.controller.BaseController;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysRole;
import com.system.springbootv1.project.model.SysUser;
import com.system.springbootv1.project.model.page.TableDataInfo;
import com.system.springbootv1.project.service.SysRoleService;
import com.system.springbootv1.project.service.SysUserService;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/05/06
 **/
@RestController
@RequestMapping("/system/role/*")
public class SysRoleApiController extends BaseController {
    @Resource
    SysRoleService roleService;
    @Resource
    SysUserService userService;

    @RequiresPermissions("role:list")
    @RequestMapping("list")
    public TableDataInfo list(SysRole role) {
        startPage();
        List<SysRole> roleList = roleService.selectRoleList(role);
        return getDataTable(roleList);
    }

    @GetMapping("{roleId}")
    public Object getInfo(@PathVariable String roleId) {
        return BaseResult.successData(roleService.getById(roleId));
    }

    @RequiresPermissions("role:edit")
    @PostMapping("edit")
    public Object edit(@Validated @RequestBody SysRole role) throws Exception {
        String roleId = "";
        if (StringUtils.isEmpty(role.getId())) {
            if (roleService.checkRoleName(role.getRoleName())) {
                return BaseResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
            }
            role.setCreator(ShiroUtils.getUserId());
            roleId = roleService.insertRole(role);
        } else {
            roleId = role.getId();
            role.setModifier(ShiroUtils.getUserId());
            roleService.updateRole(role);
        }

        return BaseResult.success();
    }

    @RequiresPermissions("role:delete")
    @DeleteMapping("{roleIds}")
    public Object remove(@PathVariable String[] roleIds) {
        roleService.deleteByIds(Arrays.asList(roleIds));
        return BaseResult.success();
    }

    @PutMapping("dataScope")
    public Object dataScope(@RequestBody SysRole role) {
        roleService.authDataScope(role);
        return BaseResult.success();
    }

    @GetMapping("optionselect")
    public Object optionselect() {
        return BaseResult.successData(roleService.getAll());
    }

    @GetMapping("roleUserselect/{roleId}")
    public Object roleUserselect(@PathVariable String roleId) {
        BaseResult result = BaseResult.success();
        result.put("allUser", userService.selectUserList(new SysUser()));
        result.put("checkedKeys", userService.getUserByRole(roleId));
        return result;
    }
}
