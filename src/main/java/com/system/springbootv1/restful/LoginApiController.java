package com.system.springbootv1.restful;

import com.system.springbootv1.common.config.BaseConfig;
import com.system.springbootv1.common.jwt.JwtToken;
import com.system.springbootv1.common.jwt.JwtUtil;
import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysMenu;
import com.system.springbootv1.project.model.SysRole;
import com.system.springbootv1.project.model.SysUser;
import com.system.springbootv1.project.service.SysMenuService;
import com.system.springbootv1.project.service.SysRoleService;
import com.system.springbootv1.project.service.SysUserService;
import com.system.springbootv1.common.redis.RedisUtil;
import com.system.springbootv1.utils.ServletUtils;
import com.system.springbootv1.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:
 * @author: yy 2020/05/04
 **/
@Controller
public class LoginApiController {
    private Logger logger = LoggerFactory.getLogger(LoginApiController.class);

    private String prefix = "";

    @Resource
    SysUserService userService;
    @Resource
    SysRoleService roleService;
    @Resource
    SysMenuService menuService;
    @Resource
    BaseConfig config;


    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = ServletUtils.getParameter();
        String userName = String.valueOf(map.get("username"));
        String password = String.valueOf(map.get("password"));
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            JwtToken jwtToken = new JwtToken(JwtUtil.sign(userName,password));
            try {
                currentUser.login(jwtToken);
                String loginToken = JwtUtil.sign(userName, password);
                if (StringUtils.isNotNull(ShiroUtils.getUser())) {
                    RedisUtil.set(loginToken, ShiroUtils.getUser(), config.getTimeout());
                    return BaseResult.success().put("token", loginToken);
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
        return BaseResult.unLogin();
    }

    @RequestMapping("loginOut")
    @ResponseBody
    public Object loginOut(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseResult.success();
    }

    @RequestMapping("unAuth")
    @ResponseBody
    public Object unAuth(HttpServletRequest request, HttpServletResponse response) {
        return BaseResult.unLogin();
    }

    @RequestMapping("unauthorized")
    @ResponseBody
    public Object unauthorized(HttpServletRequest request, HttpServletResponse response) {
        return BaseResult.error(HttpStatus.FORBIDDEN.value(), "用户无权限！");
    }

    @RequestMapping("getInfo")
    @ResponseBody
    public Object getInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysUser user = ShiroUtils.getUser();
        List<SysRole> roleList = roleService.getRoleByUserId(user.getId());
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        for (SysRole role : roleList) {
            List<String> perms = new ArrayList<>();
            if ("gly".equalsIgnoreCase(role.getRoleName())) {
                perms = roleService.getPermsByRoleId(null);
            } else {
                perms = roleService.getPermsByRoleId(role.getId());
            }
            for (String perm : perms) {
                permissions.add(perm);
            }
            roles.add(role.getRoleName());
        }
        return BaseResult.success().put("user", user).put("roles", roles).put("permissions", permissions);
    }

    @RequestMapping("getRouters")
    @ResponseBody
    public Object getRouters() throws Exception {
        SysUser user = ShiroUtils.getUser();
        List<SysMenu> menus = menuService.getAll(user.getId());
        return BaseResult.successData(HttpStatus.OK.value(), menuService.buildMenus(menus, "0"));
    }
}
