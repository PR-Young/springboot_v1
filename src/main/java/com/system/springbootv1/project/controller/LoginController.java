package com.system.springbootv1.project.controller;

import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.service.SysUserService;
import com.system.springbootv1.utils.MD5Util;
import com.system.springbootv1.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
@Api("登录")
@Controller
@RequestMapping("/login/*")
public class LoginController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private String prefix = "";

    @Resource
    SysUserService userService;

    @ApiOperation(value = "跳转登录界面", notes = "跳转登录界面")
    @RequestMapping("login")
    public String login(ModelMap modelMap) {
        try {
            if (null != SecurityUtils.getSubject() && SecurityUtils.getSubject().isAuthenticated()) {
                return "redirect:/index";
            } else {
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, MD5Util.encode(password));
            try {
                currentUser.login(token);
                if (StringUtils.isNotNull(ShiroUtils.getUser())) {
                    return BaseResult.success();
                } else {
                    return BaseResult.error(500, "未知账户");
                }
            } catch (UnknownAccountException uae) {
                logger.info("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
                return BaseResult.error(500, "未知账户");
            } catch (IncorrectCredentialsException ice) {
                logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
                return BaseResult.error(500, "用户名或密码不正确");
            } catch (LockedAccountException lae) {
                logger.info("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
                return BaseResult.error(500, "账户已锁定");
            } catch (ExcessiveAttemptsException eae) {
                logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误次数过多");
                return BaseResult.error(500, "用户名或密码错误次数过多");
            } catch (AuthenticationException ae) {
                logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
                ae.printStackTrace();
                return BaseResult.error(500, "用户名或密码不正确");
            }
        }
        return BaseResult.error(500, "用户名或密码不正确");
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login/index";
    }
}
