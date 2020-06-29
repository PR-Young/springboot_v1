package com.system.springbootv1.project.dao;

import com.system.springbootv1.project.model.SysProperties;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISysPropertiesDao extends IBaseDao<SysProperties> {

    public List<SysProperties> getByGroup(@Param("group") String group);

    public String getValueByAlias(@Param("alias") String alias);
}