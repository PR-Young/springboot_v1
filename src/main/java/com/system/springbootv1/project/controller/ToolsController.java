package com.system.springbootv1.project.controller;

import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.service.ToolService;
import com.system.springbootv1.utils.ServletUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: yy 2020/02/02
 **/
@Controller
@RequestMapping("/tool/*")
public class ToolsController {

    @Resource
    ToolService toolService;

    @RequestMapping("python")
    public String index() {
        return "/tools/python";
    }

    @RequestMapping("run")
    @ResponseBody
    public Object exec(HttpServletRequest request, HttpServletResponse response) {
        String script = ServletUtils.getParameter("script");
        String result = toolService.exec(script);
        return BaseResult.successData(200, result);
    }
}
