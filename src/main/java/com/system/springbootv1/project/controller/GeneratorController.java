package com.system.springbootv1.project.controller;

import com.github.pagehelper.PageInfo;
import com.system.springbootv1.common.autocode.CodeGenerator;
import com.system.springbootv1.common.autocode.FreeMarkerGeneratorUtil;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.TableResult;
import com.system.springbootv1.project.service.GeneratorService;
import com.system.springbootv1.utils.ServletUtils;
import com.system.springbootv1.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/04/28
 **/
@Controller
@RequestMapping("/generator/*")
public class GeneratorController {

    @Resource
    GeneratorService generatorService;
    @Resource
    CodeGenerator generator;

    @RequestMapping("tables")
    @ResponseBody
    public Object tables(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> tables = generatorService.tables(new HashMap<>());
        return tables;
    }

    @RequestMapping("columns/{tableName}")
    @ResponseBody
    public Object list(@PathVariable("tableName") String tableName, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(tableName)) {
            return new TableResult<Map<String, Object>>(1, 0L, new ArrayList<Map<String, Object>>());
        }
        Map<String, Object> map = new HashMap<>();
        map = ServletUtils.getQueryParams();
        map.put("tableName", tableName);
        PageInfo<Map<String, Object>> columnPageInfo = generatorService.columns(ServletUtils.getParameterToInt("pageNum"), ServletUtils.getParameterToInt("pageSize"), map);
        TableResult<Map<String, Object>> tableResult = new TableResult<>(columnPageInfo.getPageNum(), columnPageInfo.getTotal(), columnPageInfo.getList());
        return tableResult;
    }

    @RequestMapping("generator")
    @ResponseBody
    public Object generator(HttpServletRequest request, HttpServletResponse response) {
        String tableName = ServletUtils.getParameter("tableName");
        FreeMarkerGeneratorUtil.generatorMvcCode(
                generator.getDriverClassName(),
                generator.getUrl(),
                generator.getUsername(),
                generator.getPassword(),
                tableName,
                generator.getDatabaseName(),
                generator.getTablePrefix(),
                generator.getGenenaterLevel(),
                generator.getBasePackage(),
                generator.getDaoPackage(),
                generator.getXmlDir(),
                generator.getServicePackage(),
                generator.getControllerPackage());
        return BaseResult.success();
    }
}
