package com.system.springbootv1.project.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.springbootv1.project.model.page.PageDomain;
import com.system.springbootv1.project.model.page.TableDataInfo;
import com.system.springbootv1.project.model.page.TableSupport;
import com.system.springbootv1.utils.SqlUtil;
import com.system.springbootv1.utils.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
public class BaseController {

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.OK.value());
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    protected BaseController.MapBuilder buildMapBuilder() {
        return new MapBuilder();
    }

    protected class MapBuilder {
        Map<String, Object> map = new HashMap<>();

        protected MapBuilder() {
        }

        public BaseController.MapBuilder addParam(String key, Object value) {
            map.put(key, value);
            return this;
        }

        public Map<String, Object> getMap() {
            return map;
        }
    }
}
