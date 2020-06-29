package com.system.springbootv1.project.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.dao.ISysTemplateDao;
import com.system.springbootv1.project.model.SysTemplate;
import com.system.springbootv1.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysTemplateService implements BaseService<SysTemplate> {

    @Resource
    ISysTemplateDao sysTemplateDao;

    public PageInfo<SysTemplate> list(int pageNo, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysTemplate> lists = sysTemplateDao.list(map);
        PageInfo<SysTemplate> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(SysTemplate obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysTemplateDao.insert(obj);
    }

    @Override
    public void update(SysTemplate obj) {
        obj.setModifyTime(new Date());
        sysTemplateDao.update(obj);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysTemplateDao.deleteByIds(ids);
    }

    @Override
    public SysTemplate getById(String id) {
        return sysTemplateDao.getById(id);
    }

    public List<SysTemplate> list(SysTemplate obj) {
        List<SysTemplate> lists = sysTemplateDao.selectList(obj);
        return lists;
    }


}