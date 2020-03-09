package com.system.springbootv1.controller;

import com.github.pagehelper.PageInfo;
import com.system.springbootv1.model.BaseResult;
import com.system.springbootv1.model.SysQuartzJob;
import com.system.springbootv1.model.SysQuartzJobLog;
import com.system.springbootv1.model.TableResult;
import com.system.springbootv1.service.SysQuartzJobLogService;
import com.system.springbootv1.service.SysQuartzJobService;
import com.system.springbootv1.utils.ServletUtils;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @description:
 * @author: yy 2020/01/29
 **/
@Controller
@RequestMapping("/quartz/*")
public class SysQuartzJobController {

    @Resource
    SysQuartzJobService sysQuartzJobService;
    @Resource
    SysQuartzJobLogService sysQuartzJobLogService;

    private String prefix = "quartz";

    @RequestMapping("list")
    @RequiresPermissions("quartz:list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response) {
        PageInfo<SysQuartzJob> jobPageInfo = sysQuartzJobService.list(ServletUtils.getParameterToInt("pageNum"), ServletUtils.getParameterToInt("pageSize"), ServletUtils.getQueryParams());
        TableResult<SysQuartzJob> tableResult = new TableResult<>(jobPageInfo.getPageNum(), jobPageInfo.getTotal(), jobPageInfo.getList());
        return tableResult;
    }

    @GetMapping("add")
    public String add(ModelMap map) {
        map.put("sysQuartzJob", new SysQuartzJob());
        return prefix + "/edit";
    }


    @GetMapping("update/{jobId}")
    public String edit(@PathVariable("jobId") String id, ModelMap map) {
        map.put("sysQuartzJob", sysQuartzJobService.getById(id));
        return prefix + "/edit";
    }

    @RequestMapping("edit")
    @RequiresPermissions("quartz:edit")
    @ResponseBody
    public Object edit(SysQuartzJob sysQuartzJob, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(sysQuartzJob.getId())) {
            sysQuartzJobService.insert(sysQuartzJob);
        } else {
            sysQuartzJobService.update(sysQuartzJob);
        }
        return BaseResult.success();
    }

    @RequestMapping("delete")
    @RequiresPermissions("quartz:delete")
    @ResponseBody
    public Object delete(HttpServletRequest request, HttpServletResponse response) {
        String ids = ServletUtils.getParameter("ids");
        if (null != ids) {
            sysQuartzJobService.deleteByIds(Arrays.asList(ids.split(",")));
        }
        return BaseResult.success();
    }

    @RequestMapping("checkUnique")
    @ResponseBody
    public int checkUnique(SysQuartzJob sysQuartzJob, HttpServletRequest request, HttpServletResponse response) {
        return sysQuartzJobService.checkUnique(sysQuartzJob.getJobName());
    }

    /**
     * 任务调度状态修改
     */
    @PostMapping("changeStatus")
    @ResponseBody
    public Object changeStatus(SysQuartzJob job) throws SchedulerException {
        SysQuartzJob newJob = sysQuartzJobService.getById(job.getId());
        newJob.setStatus(job.getStatus());
        sysQuartzJobService.changeStatus(newJob);
        return BaseResult.success();
    }

    /**
     * 任务调度立即执行一次
     */
    @PostMapping("run")
    @ResponseBody
    public Object run(SysQuartzJob job) throws SchedulerException {
        SysQuartzJob newJob = sysQuartzJobService.getById(job.getId());
        sysQuartzJobService.run(newJob);
        return BaseResult.success();
    }

    @RequestMapping("logList")
    @RequiresPermissions("quartz:logList")
    @ResponseBody
    public Object logList(HttpServletRequest request, HttpServletResponse response) {
        PageInfo<SysQuartzJobLog> jobLogPageInfo = sysQuartzJobLogService.list(ServletUtils.getParameterToInt("pageNum"), ServletUtils.getParameterToInt("pageSize"), ServletUtils.getQueryParams());
        TableResult<SysQuartzJobLog> tableResult = new TableResult<>(jobLogPageInfo.getPageNum(), jobLogPageInfo.getTotal(), jobLogPageInfo.getList());
        return tableResult;
    }

}
