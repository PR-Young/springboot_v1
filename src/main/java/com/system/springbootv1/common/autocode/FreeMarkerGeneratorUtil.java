package com.system.springbootv1.common.autocode;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.system.springbootv1.common.model.ColumnType;
import com.system.springbootv1.common.model.DataType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/03/31
 **/
public class FreeMarkerGeneratorUtil {

    private static Logger log = LoggerFactory.getLogger(FreeMarkerGeneratorUtil.class);

    /**
     * 生成三层代码 包含 仅生成dao层 （包含实体Entity及mapper接口及xml） 生成service层 （包含service接口及impl） 生成controller层
     *
     * @param driver
     * @param url
     * @param user
     * @param pwd
     */
    public static void generatorMvcCode(String driver, String url, String user, String pwd, String tableName, String databaseName,
                                        String tablePrefix, int generateLevel, String basePackage, String daoPackage, String xmlDir, String servicePackage,
                                        String controllerPackage) {

        Connection con = null;
        //注册驱动
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            log.error("获取数据连接失败，{}", e.getMessage());
            return;
        }

        //查询dbName所有表
        String sql = "select table_name from information_schema.tables where table_schema='" + databaseName + "'";

        //获取当前项目路径
        String path = FreeMarkerGeneratorUtil.class.getResource("/").getPath();
        path = StrUtil.sub(path, 1, path.indexOf("/target"));

        log.info("当前项目路径为：{}", path);
        String parentProjectPath = StrUtil.sub(path, 0, path.lastIndexOf("/"));
        //获取模板路径
        String templatePath = path + "/src/main/java/com/system/springbootv1/common/templates";
        log.info("当前模板路径为：{}", templatePath);

        boolean onlySingleTable = StrUtil.isNotBlank(tableName);
        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!onlySingleTable) {
                    tableName = rs.getString(1);
                }
                String entityDir = null;
                String daoDir = null;
                String serviceDir = null;
                String controllerDir = null;
                //根据实体包名创建目录
                if (generateLevel > 0) {
                    File[] ls = FileUtil.ls(parentProjectPath);
                    for (File f : ls) {
                        String currModule = f.toString();
                        boolean matches = currModule.matches("(.*?model.*?)|(.*?domain.*?)|(.*?entity.*?)");
                        if (f.isDirectory() && matches) {
                            entityDir = f.toString() + "/src/main/java/" + basePackage.replace(".", "/");
                            break;
                        }
                    }
                    if (StrUtil.isBlank(entityDir)) {
                        entityDir = path + "/src/main/java/" + basePackage.replace(".", "/");
                    }
                    if (!FileUtil.exist(entityDir)) {
                        FileUtil.mkdir(entityDir);
                        log.info("创建目录：{} 成功！ ", entityDir);
                    }
                    for (File f : ls) {
                        String currModule = f.toString();
                        boolean matches = currModule.matches("(.*?dao.*?)|(.*?mapper.*?)");
                        if (f.isDirectory() && matches) {
                            daoDir = f.toString() + "/src/main/java/" + daoPackage.replace(".", "/");
                            break;
                        }
                    }
                    if (StrUtil.isBlank(daoDir)) {
                        daoDir = path + "/src/main/java/" + daoPackage.replace(".", "/");
                    }
                    if (!FileUtil.exist(daoDir)) {
                        FileUtil.mkdir(daoDir);
                        log.info("创建目录：{} 成功！ ", daoDir);
                    }
                    for (File f : ls) {
                        String currModule = f.toString();
                        boolean matches = currModule.matches("(.*?service.*?)");
                        if (f.isDirectory() && matches) {
                            serviceDir = f.toString() + "/src/main/java/" + servicePackage.replace(".", "/");
                            break;
                        }
                    }
                    if (StrUtil.isBlank(serviceDir)) {
                        serviceDir = path + "/src/main/java/" + servicePackage.replace(".", "/");
                    }
                    if (!FileUtil.exist(serviceDir)) {
                        FileUtil.mkdir(serviceDir);
                        log.info("创建目录：{} 成功！ ", serviceDir);
                    }
                    for (File f : ls) {
                        String currModule = f.toString();
                        boolean matches = currModule.matches("(.*?controller.*?)");
                        if (f.isDirectory() && matches) {
                            controllerDir = f.toString() + "/src/main/java/" + controllerPackage.replace(".", "/");
                            break;
                        }
                    }
                    if (StrUtil.isBlank(controllerDir)) {
                        controllerDir = path + "/src/main/java/" + controllerPackage.replace(".", "/");
                    }
                    if (!FileUtil.exist(controllerDir)) {
                        FileUtil.mkdir(controllerDir);
                        log.info("创建目录：{} 成功！ ", controllerDir);
                    }
                }
                EntityDataModel entityModel = getEntityModel(con, tableName, basePackage,daoPackage,servicePackage,controllerPackage, tablePrefix);
                //生成每个表实体
                generateCode(entityModel, templatePath, "Entity.ftl", entityDir, "",".java");
                //创建mapperxml路径

                String mapperxmlPath = null;
                //根据实体包名创建目录
                if (StrUtil.isNotBlank(xmlDir)) {
                    mapperxmlPath = path + xmlDir.replace(".", "/");
                    if (!FileUtil.exist(mapperxmlPath)) {
                        FileUtil.mkdir(mapperxmlPath);
                    }
                } else {
                    mapperxmlPath = entityDir;
                }
                generateCode(entityModel, templatePath, "EntityMapper.ftl", mapperxmlPath, "","Mapper.xml");

                generateCode(entityModel, templatePath, "EntityDao.ftl", daoDir, "I","Dao.java");

                generateCode(entityModel, templatePath, "EntityService.ftl", serviceDir, "","Service.java");

                generateCode(entityModel, templatePath, "EntityController.ftl", controllerDir, "","Controller.java");

                if (onlySingleTable) {
                    return;
                }
            }

        } catch (Exception e) {
            log.error("代码生成出错 {}", e.getMessage());
        }

    }

    private static EntityDataModel getEntityModel(Connection con, String tableName, String basePackage, String daoPackage, String servicePackage,
                                                  String controllerPackage, String tablePrefix)
            throws Exception {
        //查询表属性,格式化生成实体所需属性
        String sql = "SELECT table_name, column_name, column_comment, column_type, column_key, column_default "
                + "FROM INFORMATION_SCHEMA. COLUMNS " + "WHERE table_name = '" + tableName + "' " + "AND table_schema = 'springbootv1'";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Coloum> columns = new ArrayList<>();
        while (rs.next()) {
            Coloum col = new Coloum();
            String name = rs.getString("column_name");
            String type = rs.getString("column_type");
            String comment = rs.getString("column_comment");
            String annotation = null;
            if ("Id".equalsIgnoreCase(name) || "CreateTime".equalsIgnoreCase(name) || "ModifyTime".equalsIgnoreCase(name)) {
                continue;
            }
            type = ColumnType.getJavaType(type);
            col.setName(StrUtil.lowerFirst(StrUtil.toCamelCase(name)));
            col.setColumnName(name);
            col.setType(type);
            col.setAnnotation(annotation);
            col.setComment(comment);
            col.setJdbcType(DataType.getJdbcType(type));
            columns.add(col);
        }
        EntityDataModel dataModel = new EntityDataModel();
        dataModel.setEntityPackage(basePackage);
        dataModel.setDaoPackage(daoPackage);
        dataModel.setServicePackage(servicePackage);
        dataModel.setControllerPackage(controllerPackage);
        dataModel.setCreateTime(new Date().toString());
        if (StrUtil.isNotBlank(tablePrefix)) {
            dataModel.setEntityName(StrUtil.upperFirst(StrUtil.toCamelCase(StrUtil.removePrefix(tableName, tablePrefix))));
            dataModel.setEntityName1(StrUtil.lowerFirst(StrUtil.toCamelCase(StrUtil.removePrefix(tableName, tablePrefix))));
        } else {
            dataModel.setEntityName(StrUtil.upperFirst(StrUtil.toCamelCase(tableName)));
            dataModel.setEntityName1(StrUtil.lowerFirst(StrUtil.toCamelCase(tableName)));
        }
        dataModel.setTableName(tableName);
        dataModel.setColumns(columns);
        return dataModel;
    }

    private static String handleSQL(List<Coloum> coloums, String flag) {
        StringBuffer sb = new StringBuffer();
        switch (flag) {
            case "columnList":
                for (Coloum coloum : coloums) {
                    sb.append("`").append(coloum.getColumnName()).append("`,");
                }
                break;
            case "insert":
                sb.append("#{Id,jdbcType=VARCHAR},\n");
                for (Coloum coloum : coloums) {
                    sb.append("#{").append(coloum.getColumnName()).append(",jdbcType=").append(coloum.getJdbcType()).append("},\n");
                }
                sb.append("#{createTime,jdbcType=TIMESTAMP},\n").append("#{modifyTime,jdbcType=TIMESTAMP}");
                break;
            case "update":
                for (Coloum coloum : coloums) {
                    sb.append("<if test=\"").append(coloum.getName()).append("!= null\">\n")
                            .append("`").append(coloum.getColumnName()).append("`=#{").append(coloum.getName()).append(",jdbcType=")
                            .append(coloum.getJdbcType()).append("},").append("\n")
                            .append("</if>\n");
                }
                sb.append("<if test=\"createTime != null\">\n")
                        .append("`CreateTime` = #{createTime,jdbcType=TIMESTAMP},\n")
                        .append("</if>\n")
                        .append("<if test=\"modifyTime != null\">\n")
                        .append("`ModifyTime` = #{modifyTime,jdbcType=TIMESTAMP},\n")
                        .append("</if>");
                break;
            default:
                break;
        }
        return sb.toString();
    }

    private static void generateCode(EntityDataModel dataModel, String templatePath, String templateName, String outDir,String filePrefix, String fileSuffix)
            throws IOException, TemplateException {

        if (fileSuffix.contains("xml")) {
            dataModel.setInsertValue(handleSQL(dataModel.getColumns(), "insert"));
            dataModel.setUpdateValue(handleSQL(dataModel.getColumns(), "update"));
            dataModel.setStrColumn(handleSQL(dataModel.getColumns(), "columnList"));
        }

        String file = outDir + "/" + filePrefix + dataModel.getEntityName() + fileSuffix;
        if (FileUtil.exist(file)) {
            log.info("文件：{} 已存在，如需覆盖请先对该文件进行");
            return;
        }
        //获取模板对象
        Configuration conf = new Configuration();
        File temp = new File(templatePath);
        conf.setDirectoryForTemplateLoading(temp);
        Template template = conf.getTemplate(templateName);
        Writer writer = new FileWriter(file);
        //填充数据模型
        template.process(dataModel, writer);
        writer.close();
        log.info("代码生成成功，文件位置：{}", file);
    }
}
