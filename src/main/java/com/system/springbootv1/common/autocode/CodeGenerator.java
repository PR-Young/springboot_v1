package com.system.springbootv1.common.autocode;

import lombok.Data;

/**
 * @description:
 * @author: yy 2020/03/31
 **/
@Data
public class CodeGenerator {

    /**
     * 数据库驱动
     * 未提供注入时请自行提供驱动
     */
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    /**
     * 数据库URL
     */
    private String url = "jdbc:mysql://localhost:3306/springbootv1?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false";
    /**
     * 用户名
     */
    private String username = "root";

    /**
     * 密码
     */
    private String password = "root";

    /**
     * 数据库名
     */
    private String databaseName = "springbootv1";

    /**
     * 只生成单表，非必须
     */
    private String tableName = "test_v";

    /**
     * 表前缀，非必须
     */
    private String tablePrefix = "";

    /**
     * 生成级别 必须
     * 1 仅生成dao层
     * 2 生成service层
     * 3 生成controller层
     */
    private int genenaterLevel = 1;

    /**
     * 基本包名 必须
     */
    private String basePackage = "com.system.springbootv1.model";

    /**
     * dao接口包名 无则使用基本包名
     */
    private String daoPackage = "com.system.springbootv1.dao";

    /**
     * mapper.xml 生成路径 无则使用基本包名
     */
    private String xmlDir = ".src.main.resources.mapping";

    /**
     * servcie包名 impl也在此包路径下 无则使用基本包名
     */
    private String servicePackage = "com.system.springbootv1.service";

    /**
     * controller包名 无则使用基本包名
     */
    private String controllerPackage = "com.system.springbootv1.controller";



}
