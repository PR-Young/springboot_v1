package com.system.springbootv1.dao;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/04/28
 **/
public interface IGeneratorDao {

    public List<Map<String,Object>> getTables();

    public List<Map<String,Object>> getColumnsByTable(Map<String, Object> map);
}
