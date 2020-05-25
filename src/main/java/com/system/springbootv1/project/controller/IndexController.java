package com.system.springbootv1.project.controller;

import com.system.springbootv1.common.model.Service;
import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.project.model.BootstrapTree;
import com.system.springbootv1.project.service.GeneratorService;
import com.system.springbootv1.project.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
@Api("主页")
@Controller
@RequestMapping("/*")
public class IndexController {
    @Resource
    SysMenuService sysMenuService;
    @Resource
    GeneratorService generatorService;

    @ApiOperation(value = "前台", notes = "前台")
    @RequestMapping("system/index")
    public String index(HttpServletRequest request) throws Exception {
        BootstrapTree bootstrapTree = sysMenuService.getBootstrapTree(ShiroUtils.getUserId());
        request.setAttribute("bootstrapTree", bootstrapTree);
        request.setAttribute("userName", ShiroUtils.getUser().getUserName());
        request.setAttribute("sysUser", ShiroUtils.getUser());
        return "index";
    }

    @ApiOperation(value = "局部刷新区域", notes = "局部刷新区域")
    @RequestMapping("system/main")
    public String main(HttpServletRequest request) {
        return "main";
    }

    @ApiOperation(value = "没有权限", notes = "没有权限")
    @RequestMapping("system/noAuth")
    public String noAuth(HttpServletRequest request) {
        return "/errors/403";
    }

    @ApiOperation(value = "页面未找到", notes = "页面未找到")
    @RequestMapping("system/error404")
    public String error404(HttpServletRequest request) {
        return "/errors/404";
    }

    @ApiOperation(value = "服务器内部错误", notes = "服务器内部错误")
    @RequestMapping("system/error500")
    public String error500(HttpServletRequest request) {
        return "/errors/500";
    }

    @ApiOperation(value = "用户管理", notes = "用户管理")
    @RequestMapping("system/user/index")
    public String userIndex(HttpServletRequest request) {
        return "/user/list";
    }

    @ApiOperation(value = "菜单管理", notes = "菜单管理")
    @RequestMapping("system/menu/index")
    public String menuIndex(HttpServletRequest request) {
        return "/menu/list";
    }

    @ApiOperation(value = "任务管理", notes = "任务管理")
    @RequestMapping("monitor/job/index")
    public String jobIndex(HttpServletRequest request) {
        return "/quartz/list";
    }

    @ApiOperation(value = "角色管理", notes = "角色管理")
    @RequestMapping("system/role/index")
    public String roleIndex(HttpServletRequest request) {
        return "/role/list";
    }

    @ApiOperation(value = "任务日志", notes = "任务日志")
    @RequestMapping("monitor/job/log")
    public String jobLogIndex(HttpServletRequest request) {
        return "/quartz/log_list";
    }

    @ApiOperation(value = "字体图标", notes = "字体图标")
    @RequestMapping("system/fontawesome/index")
    public String fontawesome(HttpServletRequest request) {
        return "/sys/fontawesome";
    }

    @ApiOperation(value = "消息测试", notes = "消息测试")
    @RequestMapping("system/message")
    public String message(HttpServletRequest request) {
        return "/message/message";
    }

    @ApiOperation(value = "服务器信息类", notes = "服务器信息类")
    @RequestMapping("monitor/server/index")
    public String service(HttpServletRequest request) {
        request.setAttribute("service", new Service());
        return "/sys/service";
    }

    @RequestMapping("tool/gen/index")
    public String generator(HttpServletRequest request) {
        List<Map<String, Object>> tables = generatorService.tables(new HashMap<>());
        request.setAttribute("tables", tables);
        return "autocode/list";
    }

}
