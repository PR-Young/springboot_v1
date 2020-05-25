package com.system.springbootv1.project.dao;

import com.system.springbootv1.project.model.SysQuartzJob;

public interface ISysQuartzJobDao extends IBaseDao<SysQuartzJob>{

    Integer checkUnique(String jobName);

}