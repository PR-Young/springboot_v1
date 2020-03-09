package com.system.springbootv1.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.springbootv1.dao.ISysQuartzJobLogDao;
import com.system.springbootv1.model.SysQuartzJobLog;
import com.system.springbootv1.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/29
 **/
@Service
public class SysQuartzJobLogService implements BaseService<SysQuartzJobLog>{

    @Resource
    ISysQuartzJobLogDao sysQuartzJobLogDao;

    @Override
    public void insert(SysQuartzJobLog obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        sysQuartzJobLogDao.insert(obj);
    }

    @Override
    public void update(SysQuartzJobLog obj) {

    }

    @Override
    public void deleteByIds(List<String> ids) {

    }

    @Override
    public SysQuartzJobLog getById(String id) {
        return null;
    }

    public PageInfo<SysQuartzJobLog> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysQuartzJobLog> sysQuartzJobLogList = sysQuartzJobLogDao.list(map);
        PageInfo<SysQuartzJobLog> jobPageInfo = new PageInfo<>(sysQuartzJobLogList);
        return jobPageInfo;
    }
}
