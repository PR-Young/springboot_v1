package com.system.springbootv1.project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.dao.ISysLogDao;
import com.system.springbootv1.project.model.SysLog;
import com.system.springbootv1.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/27
 **/
@Service
public class SysLogService {

    @Resource
    ISysLogDao sysLogDao;

    public void insert(SysLog sysLog) {
        sysLog.setId(SnowflakeIdWorker.getUUID());
        sysLog.setCreateTime(new Date());
        sysLogDao.insert(sysLog);
    }

    public PageInfo<SysLog> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysLog> userList = sysLogDao.list(map);
        PageInfo<SysLog> userPageInfo = new PageInfo<>(userList);
        return userPageInfo;
    }
}
