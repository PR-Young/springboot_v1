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
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @description:
 * @author: yy 2020/03/31
 **/
@Component
public class FreeMarkerGeneratorUtil {

    private static Logger log = LoggerFactory.getLogger(FreeMarkerGeneratorUtil.class);

    @Resource
    CodeGenerator generator;
    private static CodeGenerator codeGenerator;

    @PostConstruct
    public void init() {
        codeGenerator = generator;
    }

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
                    entityDir = createDir(parentProjectPath, path, basePackage, "(.*?model.*?)");
                    daoDir = createDir(parentProjectPath, path, daoPackage, "(.*?dao.*?)");
                    serviceDir = createDir(parentProjectPath, path, servicePackage, "(.*?service.*?)");
                    controllerDir = createDir(parentProjectPath, path, controllerPackage, "(.*?controller.*?)");
                }
                EntityDataModel entityModel = getEntityModel(con, tableName, basePackage, daoPackage, servicePackage, controllerPackage, tablePrefix);
                //生成java文件
                generateCode(entityModel, templatePath, "Entity.ftl", entityDir, "", ".java");
                generateCode(entityModel, templatePath, "EntityDao.ftl", daoDir, "I", "Dao.java");
                generateCode(entityModel, templatePath, "EntityService.ftl", serviceDir, "", "Service.java");
                generateCode(entityModel, templatePath, "EntityController.ftl", controllerDir, "", "Controller.java");
                //创建mapper路径
                String mapperxmlPath = null;
                //根据创建xml目录
                if (StrUtil.isNotBlank(xmlDir)) {
                    mapperxmlPath = path + xmlDir.replace(".", "/");
                    if (!FileUtil.exist(mapperxmlPath)) {
                        FileUtil.mkdir(mapperxmlPath);
                    }
                } else {
                    mapperxmlPath = entityDir;
                }
                generateCode(entityModel, templatePath, "EntityMapper.ftl", mapperxmlPath, "", "Mapper.xml");
                if (onlySingleTable) {
                    return;
                }
            }
        } catch (Exception e) {
            log.error("代码生成出错 {}", e.getMessage());
        }
    }

    private static String createDir(String parentProjectPath, String path, String pack, String regex) {
        String dir = null;
        File[] ls = FileUtil.ls(parentProjectPath);
        for (File f : ls) {
            String currModule = f.toString();
            boolean matches = currModule.matches(regex);
            if (f.isDirectory() && matches) {
                dir = f.toString() + "/src/main/java/" + pack.replace(".", "/");
                break;
            }
        }
        if (StrUtil.isBlank(dir)) {
            dir = path + "/src/main/java/" + pack.replace(".", "/");
        }
        if (!FileUtil.exist(dir)) {
            FileUtil.mkdir(dir);
            log.info("创建目录：{} 成功！ ", dir);
        }
        return dir;
    }

    private static EntityDataModel getEntityModel(Connection con, String tableName, String basePackage, String daoPackage, String servicePackage,
                                                  String controllerPackage, String tablePrefix)
            throws Exception {
        //查询表属性,格式化生成实体所需属性
        String sql = "SELECT table_name, column_name, column_comment, column_type, column_key, column_default "
                + "FROM INFORMATION_SCHEMA. COLUMNS " + "WHERE table_name = '" + tableName + "' " + "AND table_schema = 'springbootv1'";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Column> columns = new ArrayList<>();
        while (rs.next()) {
            Column col = new Column();
            String name = rs.getString("column_name");
            String type = rs.getString("column_type");
            String comment = rs.getString("column_comment");
            String annotation = null;
            if ("Id".equalsIgnoreCase(name)
                    || "CreateTime".equalsIgnoreCase(name)
                    || "ModifyTime".equalsIgnoreCase(name)
                    || "Creator".equalsIgnoreCase(name)
                    || "Modifier".equalsIgnoreCase(name)) {
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

    private static String handleSQL(List<Column> columns, String flag) {
        StringBuffer sb = new StringBuffer();
        switch (flag) {
            case "columnList":
                for (Column column : columns) {
                    sb.append("`").append(column.getColumnName()).append("`,");
                }
                break;
            case "insert":
                sb.append("#{Id,jdbcType=VARCHAR},\n");
                for (Column column : columns) {
                    sb.append("#{").append(column.getName()).append(",jdbcType=").append(column.getJdbcType()).append("},\n");
                }
                sb.append("#{creator,jdbcType=VARCHAR},\n")
                        .append("#{createTime,jdbcType=TIMESTAMP},\n")
                        .append("#{modifier,jdbcType=VARCHAR},\n")
                        .append("#{modifyTime,jdbcType=TIMESTAMP}");
                break;
            case "update":
                for (Column column : columns) {
                    sb.append("<if test=\"").append(column.getName()).append("!= null\">\n")
                            .append("`").append(column.getColumnName()).append("`=#{").append(column.getName()).append(",jdbcType=")
                            .append(column.getJdbcType()).append("},").append("\n")
                            .append("</if>\n");
                }
                sb.append("<if test=\"creator != null\">\n")
                        .append("`Creator` = #{creator,jdbcType=TIMESTAMP},\n")
                        .append("</if>\n")
                        .append("<if test=\"createTime != null\">\n")
                        .append("`CreateTime` = #{createTime,jdbcType=TIMESTAMP},\n")
                        .append("</if>\n")
                        .append("<if test=\"modifier != null\">\n")
                        .append("`Modifier` = #{modifier,jdbcType=TIMESTAMP},\n")
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

    private static void generateCode(EntityDataModel dataModel, String templatePath, String templateName, String outDir, String filePrefix, String fileSuffix)
            throws IOException, TemplateException {
        if (fileSuffix.contains("xml")) {
            dataModel.setInsertValue(handleSQL(dataModel.getColumns(), "insert"));
            dataModel.setUpdateValue(handleSQL(dataModel.getColumns(), "update"));
            dataModel.setStrColumn(handleSQL(dataModel.getColumns(), "columnList"));
        }

        String file = outDir + "/" + filePrefix + dataModel.getEntityName() + fileSuffix;
        if (FileUtil.exist(file)) {
            boolean delete = FileUtil.del(file);
            log.info("文件：{} 已存在，删除:{}", file, delete);
            if (!delete) {
                return;
            }
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

    private static Connection getConn(String driver, String url, String user, String pwd) {
        Connection con = null;
        //注册驱动
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            log.error("获取数据连接失败，{}", e.getMessage());
        }
        return con;
    }

    public static byte[] generator(String tableName) {
        return generator(codeGenerator.getDriverClassName(),
                codeGenerator.getUrl(),
                codeGenerator.getUsername(),
                codeGenerator.getPassword(),
                tableName,
                codeGenerator.getDatabaseName(),
                codeGenerator.getTablePrefix());
    }

    public static byte[] generator(String driver, String url, String user, String pwd, String tableName, String databaseName,
                                   String tablePrefix) {
        Connection con = getConn(driver, url, user, pwd);
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

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!onlySingleTable) {
                    tableName = rs.getString(1);
                }
                String entityDir = autoCodePath();
                //根据实体包名创建目录
                if (!FileUtil.exist(entityDir)) {
                    FileUtil.mkdir(entityDir);
                    log.info("创建目录：{} 成功！ ", entityDir);
                }

                EntityDataModel entityModel = getEntityModel(con,
                        tableName,
                        codeGenerator.getBasePackage(),
                        codeGenerator.getDaoPackage(),
                        codeGenerator.getServicePackage(),
                        codeGenerator.getControllerPackage(),
                        tablePrefix);
                //生成java文件
                generateCode(entityModel, templatePath, "Entity.ftl", entityDir, "", ".java", zip);
                generateCode(entityModel, templatePath, "EntityDao.ftl", entityDir, "I", "Dao.java", zip);
                generateCode(entityModel, templatePath, "EntityService.ftl", entityDir, "", "Service.java", zip);
                generateCode(entityModel, templatePath, "EntityController.ftl", entityDir, "", "Controller.java", zip);
                generateCode(entityModel, templatePath, "EntityMapper.ftl", entityDir, "", "Mapper.xml", zip);
                generateCode(entityModel, templatePath, "EntityApiController.ftl", entityDir, "", "ApiController.java", zip);
                if (onlySingleTable) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("代码生成出错 {}", e.getMessage());
        } finally {
            try {
                zip.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();
    }

    private static void generateCode(EntityDataModel dataModel, String templatePath, String templateName, String outDir, String filePrefix, String fileSuffix, ZipOutputStream zip)
            throws IOException, TemplateException {
        if (fileSuffix.contains("xml")) {
            dataModel.setInsertValue(handleSQL(dataModel.getColumns(), "insert"));
            dataModel.setUpdateValue(handleSQL(dataModel.getColumns(), "update"));
            dataModel.setStrColumn(handleSQL(dataModel.getColumns(), "columnList"));
        }

        String file = outDir + File.separator + filePrefix + dataModel.getEntityName() + fileSuffix;
        if (FileUtil.exist(file)) {
            boolean delete = FileUtil.del(file);
            log.info("文件：{} 已存在，删除:{}", file, delete);
            if (!delete) {
                return;
            }
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

        FileInputStream inputStream = new FileInputStream(new File(file));
        zip.putNextEntry(new ZipEntry(new File(file).getName()));
        byte[] buffer = new byte[inputStream.available()];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            zip.write(buffer, 0, len);
        }
        zip.flush();
        zip.closeEntry();
        inputStream.close();
    }


    public static String autoCodePath() {
        //获取当前项目路径
        String path = FreeMarkerGeneratorUtil.class.getResource("/").getPath();
        path = StrUtil.sub(path, 1, path.indexOf("/target"));
        return path + File.separator + "autocode";
    }
}
