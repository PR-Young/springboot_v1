package com.system.springbootv1.project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.dao.IGeneratorDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/04/28
 **/
@Service
public class GeneratorService {

    @Resource
    IGeneratorDao generatorDao;

    public List<Map<String, Object>> tables(Map<String, Object> map) {
        List<Map<String, Object>> tables = generatorDao.getTables(map);
        return tables;
    }

    public PageInfo<Map<String, Object>> columns(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> columnList = generatorDao.getColumnsByTable(map);
        PageInfo<Map<String, Object>> columnPageInfo = new PageInfo<>(columnList);
        return columnPageInfo;
    }

    public List<Map<String, Object>> selectTableColumnListByTableName(String tableName) {
        return generatorDao.selectTableColumnListByTableName(tableName);
    }

}
