package com.system.springbootv1.common.autocode;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: yy 2020/03/31
 **/
@Configuration
@ConfigurationProperties(prefix = "ares.codegenerator")
public class CodeGenerator {
    /**
     * 数据库驱动
     * 未提供注入时请自行提供驱动
     */
    private String driverClassName;
    /**
     * 数据库URL
     */
    private String url;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库名
     */
    private String databaseName;

    /**
     * 只生成单表，非必须
     */
    private String tableName;

    /**
     * 表前缀，非必须
     */
    private String tablePrefix;

    /**
     * 生成级别 必须
     * 1 仅生成dao层
     * 2 生成service层
     * 3 生成controller层
     */
    private int genenaterLevel;

    /**
     * 基本包名 必须
     */
    private String basePackage;

    /**
     * dao接口包名 无则使用基本包名
     */
    private String daoPackage;

    /**
     * xml 生成路径 无则使用基本包名
     */
    private String xmlDir;

    /**
     * servcie包名 无则使用基本包名
     */
    private String servicePackage;

    /**
     * controller包名 无则使用基本包名
     */
    private String controllerPackage;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public int getGenenaterLevel() {
        return genenaterLevel;
    }

    public void setGenenaterLevel(int genenaterLevel) {
        this.genenaterLevel = genenaterLevel;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getXmlDir() {
        return xmlDir;
    }

    public void setXmlDir(String xmlDir) {
        this.xmlDir = xmlDir;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }
}
