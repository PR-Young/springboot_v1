package com.system.springbootv1.restful;

import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.project.controller.BaseController;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysUser;
import com.system.springbootv1.project.model.page.TableDataInfo;
import com.system.springbootv1.project.service.SysRoleService;
import com.system.springbootv1.project.service.SysUserService;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/05/05
 **/
@RestController
@RequestMapping("/system/user/*")
public class SysUserApiController extends BaseController {

    @Resource
    SysUserService userService;
    @Resource
    SysRoleService roleService;

    @RequiresPermissions("user:list")
    @RequestMapping("list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> userList = userService.selectUserList(user);
        return getDataTable(userList);
    }

    @GetMapping(value = {"", "{userId}"})
    public Object getInfo(@PathVariable(value = "userId", required = false) String userId) {
        BaseResult result = new BaseResult();
        result.put("code", HttpStatus.OK.value());
        result.put("data", userService.getById(userId));
        result.put("roles", roleService.getAll());
        if (StringUtils.isNotEmpty(userId)) {
            result.put("roleIds", roleService.getRoleIdsByUser(userId));
        }
        return result;
    }

    @RequiresPermissions("user:edit")
    @PostMapping("edit")
    public Object edit(@Validated @RequestBody SysUser user) throws Exception {
        String userId = "";
        if (StringUtils.isEmpty(user.getId())) {
            if (userService.checkAccount(user.getAccount()) != 0) {
                return BaseResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
            }
            user.setCreator(ShiroUtils.getUserId());
            userId = userService.insertUser(user);
        } else {
            userId = user.getId();
            user.setModifier(ShiroUtils.getUserId());
            userService.update(user);
        }
        if (StringUtils.isNotEmpty(user.getRoleIds())) {
            roleService.saveRoleUser(user.getRoleIds(), userId);
        }
        return BaseResult.success();
    }

    @RequiresPermissions("user:delete")
    @DeleteMapping("{userIds}")
    public Object remove(@PathVariable String[] userIds) {
        userService.deleteByIds(Arrays.asList(userIds));
        return BaseResult.success();
    }

    /**
     * 重置密码
     */
    @PutMapping("resetPwd")
    public Object resetPwd(@RequestBody SysUser user) {
        userService.resetPassword(user.getId());
        return BaseResult.success();
    }
}
