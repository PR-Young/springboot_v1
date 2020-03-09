package com.system.springbootv1.dao;

import com.system.springbootv1.model.SysQuartzJob;

public interface ISysQuartzJobDao extends BaseDao<SysQuartzJob>{

    Integer checkUnique(String jobName);

}