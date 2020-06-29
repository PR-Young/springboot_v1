package com.system.springbootv1.project.controller;

import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysTemplate;
import com.system.springbootv1.project.model.TableResult;
import com.system.springbootv1.project.service.SysTemplateService;
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

@Api("")
@Controller
@RequestMapping("/template/*")
public class SysTemplateController extends BaseController {

    @Resource
    SysTemplateService sysTemplateService;

    private String prefix = "sysTemplate";

    @ApiOperation(value = "")
    @RequestMapping("list")
    @RequiresPermissions("sysTemplate:list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response) {
        PageInfo<SysTemplate> pageInfo = sysTemplateService.list(ServletUtils.getParameterToInt("pageNum"), ServletUtils.getParameterToInt("pageSize"), ServletUtils.getQueryParams());
        TableResult<SysTemplate> tableResult = new TableResult<>(pageInfo.getPageNum(), pageInfo.getTotal(), pageInfo.getList());
        return tableResult;
    }

    @ApiOperation(value = "跳转新增页面")
    @GetMapping("add")
    public String add(ModelMap map) {
        map.put("sysTemplate", new SysTemplate());
        return prefix + "/edit";
    }

    @ApiOperation(value = "跳转编辑页面")
    @GetMapping("update/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map) {
        map.put("sysTemplate", sysTemplateService.getById(id));
        return prefix + "/edit";
    }

    @ApiOperation(value = "新增/修改保存")
    @RequestMapping("edit")
    @RequiresPermissions("sysTemplate:edit")
    @ResponseBody
    public Object edit(SysTemplate sysTemplate, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(sysTemplate.getId())) {
            sysTemplateService.insert(sysTemplate);
        } else {
            sysTemplateService.update(sysTemplate);
        }
        return BaseResult.success();
    }

    @ApiOperation(value = "删除操作")
    @RequestMapping("delete")
    @RequiresPermissions("sysTemplate:delete")
    @ResponseBody
    public Object delete(HttpServletRequest request, HttpServletResponse response) {
        String ids = ServletUtils.getParameter("ids");
        if (null != ids) {
            sysTemplateService.deleteByIds(Arrays.asList(ids.split(",")));
        }
        return BaseResult.success();
    }
}