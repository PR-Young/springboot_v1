package com.system.springbootv1.project.dao;

import com.system.springbootv1.project.model.SysTemplate;
import org.apache.ibatis.annotations.Param;

public interface ISysTemplateDao extends IBaseDao<SysTemplate>{
    SysTemplate getByName(@Param("name") String name);
}