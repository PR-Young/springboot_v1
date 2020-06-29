package com.system.springbootv1.project.model.page;


import com.system.springbootv1.utils.ServletUtils;

/**
 * 表格数据处理
 */
public class TableSupport {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = PageDomain.newInstance()
                .withPageNum(ServletUtils.getParameterToInt(PAGE_NUM))
                .withPageSize(ServletUtils.getParameterToInt(PAGE_SIZE))
                .withOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN))
                .withIsAsc(ServletUtils.getParameter(IS_ASC))
                .build();
        return pageDomain;
    }

    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }
}
