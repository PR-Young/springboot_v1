package com.system.springbootv1.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.springbootv1.common.quartz.ScheduleConstants;
import com.system.springbootv1.common.quartz.ScheduleManager;
import com.system.springbootv1.dao.ISysQuartzJobDao;
import com.system.springbootv1.model.SysQuartzJob;
import com.system.springbootv1.utils.SnowflakeIdWorker;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/29
 **/
@Service
public class SysQuartzJobService implements BaseService<SysQuartzJob> {

    @Resource
    ISysQuartzJobDao sysQuartzJobDao;
    @Autowired
    ScheduleManager scheduler;

    public PageInfo<SysQuartzJob> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysQuartzJob> sysQuartzJobList = sysQuartzJobDao.list(map);
        PageInfo<SysQuartzJob> jobPageInfo = new PageInfo<>(sysQuartzJobList);
        return jobPageInfo;
    }

    public List<SysQuartzJob> list() {
        List<SysQuartzJob> sysQuartzJobList = sysQuartzJobDao.list(new HashMap<>());
        return sysQuartzJobList;
    }

    @Override
    public void insert(SysQuartzJob obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysQuartzJobDao.insert(obj);
    }

    @Override
    public void update(SysQuartzJob obj) {
        obj.setModifyTime(new Date());
        sysQuartzJobDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysQuartzJobDao.deleteByIds(ids);
    }

    @Override
    public SysQuartzJob getById(String id) {
        return sysQuartzJobDao.getById(id);
    }

    public int checkUnique(String jobName) {
        Integer num = sysQuartzJobDao.checkUnique(jobName);
        if (null != num && num > 0) {
            return num;
        }
        return 0;
    }

    @Transactional
    public int resumeJob(SysQuartzJob job) {
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = sysQuartzJobDao.update(job);
        if (rows > 0) {
            scheduler.start(ScheduleConstants.TASK_CLASS_NAME + job.getId(), job.getJobGroup());
        }
        return rows;
    }

    @Transactional
    public int pauseJob(SysQuartzJob job) {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = sysQuartzJobDao.update(job);
        if (rows > 0) {
            scheduler.pause(ScheduleConstants.TASK_CLASS_NAME + job.getId(), job.getJobGroup());
        }
        return rows;
    }

    @Transactional
    public int changeStatus(SysQuartzJob job) {
        int rows = 0;
        Integer status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }


    @Transactional
    public void run(SysQuartzJob job) throws SchedulerException {
        scheduler.run(job);
    }

}
