package com.system.springbootv1.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/22
 **/
public interface BaseDao<T> {
    int insert(T obj);

    int update(T obj);

    int deleteByIds(List<String> ids);

    List<T> list(Map<String,Object> params);

    T getById(@Param("id") String id);

}
