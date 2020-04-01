package com.system.springbootv1.common.autocode;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/03/31
 **/
@Data
public class EntityDataModel {
    /**
     * base package
     */
    private String entityPackage;

    private String daoPackage;

    private String servicePackage;

    private String controllerPackage;
    /**
     * 文件名后缀
     */
    private String fileSuffix;

    /**
     * 实体名
     */
    private String entityName;

    private String entityName1;

    /**
     * 作者 默认
     */
    private String author="";

    /**
     * 创建时间
     */
    private String createTime = new Date().toString();

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段集合
     */
    private List<Coloum> columns;

    private String strColumn;

    private String insertValue;

    private String updateValue;

    private String whereIdSql = "where `Id` = #{id,jdbcType=VARCHAR}";

    private String specialId = "#{id}";

    private String specialSort = "${sort}";
}
