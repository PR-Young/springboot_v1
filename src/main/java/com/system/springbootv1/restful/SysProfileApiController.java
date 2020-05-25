package com.system.springbootv1.restful;

import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.project.controller.BaseController;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysUser;
import com.system.springbootv1.project.service.SysUserService;
import com.system.springbootv1.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 个人信息 业务处理
 */
@RestController
@RequestMapping("/system/user/profile/*")
public class SysProfileApiController extends BaseController {
    @Autowired
    private SysUserService userService;


    /**
     * 个人信息
     */
    @GetMapping("info")
    public Object profile() throws Exception {
        SysUser user = ShiroUtils.getUser();
        BaseResult result = BaseResult.successData(user);
        result.put("roleGroup", userService.selectUserRoleGroup(user.getId()));
        result.put("postGroup", "");
        return result;
    }

    /**
     * 修改用户
     */
    @PutMapping("update")
    public Object updateProfile(@RequestBody SysUser user) {
        userService.update(user);
        ShiroUtils.setUser(user);
        return BaseResult.success();
    }

    /**
     * 重置密码
     */
    @PutMapping("updatePwd")
    public Object updatePwd(String oldPassword, String newPassword) throws Exception {
        SysUser user = ShiroUtils.getUser();
        if (!user.getPassword().equals(MD5Util.encode(oldPassword))) {
            return BaseResult.error("修改密码失败，旧密码错误");
        }
        if (user.getPassword().equals(MD5Util.encode(newPassword))) {
            return BaseResult.error("新密码不能与旧密码相同");
        }
        if (userService.updatePassword(user, newPassword) > 0) {
            user.setPassword(MD5Util.encode(newPassword));
            ShiroUtils.setUser(user);
            // 更新缓存用户密码
            return BaseResult.success();
        }
        return BaseResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @PostMapping("avatar")
    public Object avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
//            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
//            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
//                AjaxResult ajax = AjaxResult.success();
//                ajax.put("imgUrl", avatar);
//                // 更新缓存用户头像
//                loginUser.getUser().setAvatar(avatar);
//                tokenService.setLoginUser(loginUser);
//                return ajax;
//            }
            BaseResult.success();
        }
        return BaseResult.error("上传图片异常，请联系管理员");
    }
}
