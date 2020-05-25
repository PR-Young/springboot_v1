package com.system.springbootv1.restful;

import com.system.springbootv1.project.controller.BaseController;
import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.SysQuartzJobLog;
import com.system.springbootv1.project.model.page.TableDataInfo;
import com.system.springbootv1.project.service.SysQuartzJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/05/07
 **/
@RestController
@RequestMapping("/monitor/jobLog/*")
public class SysJobLogApiController extends BaseController {

    @Resource
    SysQuartzJobLogService jobLogService;

    @RequiresPermissions("quartz:logList")
    @GetMapping("list")
    public TableDataInfo list(SysQuartzJobLog jobLog) {
        startPage();
        List<SysQuartzJobLog> list = jobLogService.selectJobLogList(jobLog);
        return getDataTable(list);
    }

    /**
     * 根据调度编号获取详细信息
     */
    @GetMapping(value = "{jobLogId}")
    public Object getInfo(@PathVariable String jobLogId) {
        return BaseResult.successData(jobLogService.getById(jobLogId));
    }


    /**
     * 删除定时任务调度日志
     */
    @RequiresPermissions("quartz:logDelete")
    @DeleteMapping("{jobLogIds}")
    public Object remove(@PathVariable String[] jobLogIds) {
        jobLogService.deleteByIds(Arrays.asList(jobLogIds));
        return BaseResult.success();
    }

    @RequiresPermissions("quartz:logDelete")
    @DeleteMapping("clean")
    public Object clean() {
        jobLogService.cleanJobLog();
        return BaseResult.success();
    }
}
