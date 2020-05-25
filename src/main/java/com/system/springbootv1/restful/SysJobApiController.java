package com.system.springbootv1.restful;

import com.system.springbootv1.common.shiro.ShiroUtils;
import com.system.springbootv1.project.controller.BaseController;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysQuartzJob;
import com.system.springbootv1.project.model.page.TableDataInfo;
import com.system.springbootv1.project.service.SysQuartzJobService;
import com.system.springbootv1.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/05/06
 **/
@RestController
@RequestMapping("/monitor/job/*")
public class SysJobApiController extends BaseController {

    @Resource
    SysQuartzJobService jobService;

    @RequiresPermissions("quartz:list")
    @RequestMapping("list")
    public TableDataInfo list(SysQuartzJob job) {
        startPage();
        List<SysQuartzJob> jobList = jobService.selectJobList(job);
        return getDataTable(jobList);
    }

    @GetMapping("{jobId}")
    public Object getInfo(@PathVariable String jobId) {
        return BaseResult.successData(jobService.getById(jobId));
    }

    @RequiresPermissions("quartz:edit")
    @PostMapping("edit")
    public Object edit(@Validated @RequestBody SysQuartzJob job) throws Exception {
        if (StringUtils.isEmpty(job.getId())) {
            if (jobService.checkUnique(job.getJobName()) != 0) {
                return BaseResult.error("新增任务'" + job.getJobName() + "'失败，任务名称已存在");
            }
            job.setCreator(ShiroUtils.getUserId());
            jobService.insert(job);
        } else {
            job.setModifier(ShiroUtils.getUserId());
            jobService.update(job);
        }
        return BaseResult.success();
    }

    @RequiresPermissions("quartz:delete")
    @DeleteMapping("{jobIds}")
    public Object remove(@PathVariable String[] jobIds) {
        jobService.deleteByIds(Arrays.asList(jobIds));
        return BaseResult.success();
    }

    @PutMapping("changeStatus")
    public Object changeStatus(@RequestBody SysQuartzJob job) throws Exception {
        SysQuartzJob newJob = jobService.getById(job.getId());
        newJob.setStatus(job.getStatus());
        newJob.setModifier(ShiroUtils.getUserId());
        jobService.changeStatus(newJob);
        return BaseResult.success();
    }

    /**
     * 定时任务立即执行一次
     */
    @PutMapping("run")
    public Object run(@RequestBody SysQuartzJob job) throws SchedulerException {
        SysQuartzJob newJob = jobService.getById(job.getId());
        jobService.run(newJob);
        return BaseResult.success();
    }
}
