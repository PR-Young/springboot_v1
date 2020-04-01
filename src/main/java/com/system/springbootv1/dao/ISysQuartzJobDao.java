package com.system.springbootv1.dao;

import com.system.springbootv1.model.SysQuartzJob;

public interface ISysQuartzJobDao extends IBaseDao<SysQuartzJob>{

    Integer checkUnique(String jobName);

}