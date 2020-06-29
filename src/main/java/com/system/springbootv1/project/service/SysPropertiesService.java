package com.system.springbootv1.project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.dao.ISysPropertiesDao;
import com.system.springbootv1.project.model.SysProperties;
import com.system.springbootv1.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysPropertiesService implements BaseService<SysProperties>{

    @Resource
    ISysPropertiesDao sysPropertiesDao;

    public PageInfo<SysProperties> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysProperties> lists = sysPropertiesDao.list(map);
        PageInfo<SysProperties> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysProperties obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysPropertiesDao.insert(obj);
    }

    @Override
    public void update(SysProperties obj) {
        obj.setModifyTime(new Date());
        sysPropertiesDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysPropertiesDao.deleteByIds(ids);
    }

    @Override
    public SysProperties getById(String id) {
        return sysPropertiesDao.getById(id);
    }

    public List<SysProperties> list(SysProperties obj) {
        List<SysProperties> lists = sysPropertiesDao.selectList(obj);
        return lists;
    }

    public Map<String, Object> getByGroup(String group){
        List<SysProperties> properties = sysPropertiesDao.getByGroup(group);
        Map<String,Object> map = properties.stream().collect(Collectors.toMap(SysProperties::getAlias,SysProperties::getValue));
        return map;
    }

    public String getValueByAlias(String alias){
        return sysPropertiesDao.getValueByAlias(alias);
    }

}