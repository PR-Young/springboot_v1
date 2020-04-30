package com.system.springbootv1.common.model;

/**
 * @description:
 * @author: yy 2020/03/31
 **/
public enum DataType {

    VARCHAR("varchar", "VARCHAR"),
    TIMESTAMP("datetime", "TIMESTAMP"),
    INTEGER("int", "INTEGER"),
    BIGINT("bigint","BIGINT"),
    FLOAT("float","FLOAT"),
    DOUBLE("double","DOUBLE"),
    NUMERIC("numeric","NUMERIC"),
    DECIMAL("decimal","DECIMAL"),
    LONGVARCHAR("longvarchar","LONGVARCHAR");

    private String type;

    private String jdbcType;

    DataType(String type, String jdbcType) {
        this.jdbcType = jdbcType;
        this.type = type;
    }

    public static String getJdbcType(String type) {
        String result = "";
        for (DataType jdbcType : values()) {
            if (type.equalsIgnoreCase(jdbcType.type)) {
                result = jdbcType.jdbcType;
            }
        }
        return "".equals(result) ? "VARCHAR" : result;
    }
}
