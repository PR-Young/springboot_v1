package com.system.springbootv1.project.controller;

import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysProperties;
import com.system.springbootv1.project.model.TableResult;
import com.system.springbootv1.project.service.SysPropertiesService;
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
@RequestMapping("/properties/*")
public class SysPropertiesController extends BaseController {

    @Resource
    SysPropertiesService sysPropertiesService;

    private String prefix = "sysProperties";

    @ApiOperation(value = "")
    @RequestMapping("list")
    @RequiresPermissions("sysProperties:list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response) {
        PageInfo<SysProperties> pageInfo = sysPropertiesService.list(ServletUtils.getParameterToInt("pageNum"), ServletUtils.getParameterToInt("pageSize"), ServletUtils.getQueryParams());
        TableResult<SysProperties> tableResult = new TableResult<>(pageInfo.getPageNum(), pageInfo.getTotal(), pageInfo.getList());
        return tableResult;
    }

    @ApiOperation(value = "跳转新增页面")
    @GetMapping("add")
    public String add(ModelMap map) {
        map.put("sysProperties", new SysProperties());
        return prefix + "/edit";
    }

    @ApiOperation(value = "跳转编辑页面")
    @GetMapping("update/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map) {
        map.put("sysProperties", sysPropertiesService.getById(id));
        return prefix + "/edit";
    }

    @ApiOperation(value = "新增/修改保存")
    @RequestMapping("edit")
    @RequiresPermissions("sysProperties:edit")
    @ResponseBody
    public Object edit(SysProperties sysProperties, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(sysProperties.getId())) {
            sysPropertiesService.insert(sysProperties);
        } else {
            sysPropertiesService.update(sysProperties);
        }
        return BaseResult.success();
    }

    @ApiOperation(value = "删除操作")
    @RequestMapping("delete")
    @RequiresPermissions("sysProperties:delete")
    @ResponseBody
    public Object delete(HttpServletRequest request, HttpServletResponse response) {
        String ids = ServletUtils.getParameter("ids");
        if (null != ids) {
            sysPropertiesService.deleteByIds(Arrays.asList(ids.split(",")));
        }
        return BaseResult.success();
    }
}