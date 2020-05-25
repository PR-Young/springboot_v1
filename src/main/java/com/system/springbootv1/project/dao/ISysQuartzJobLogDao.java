package com.system.springbootv1.project.dao;

import com.system.springbootv1.project.model.SysQuartzJobLog;

public interface ISysQuartzJobLogDao extends IBaseDao<SysQuartzJobLog> {

    int cleanJobLog();

}