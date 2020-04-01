package com.system.springbootv1.common.autocode;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: yy 2020/03/31
 **/
@Data
public class Coloum implements Serializable {
    /**
     * 属性注解
     */
    private String annotation;

    /**
     * 属性名
     */
    private String name;

    /**
     * 属性类型
     */
    private String type;

    /**
     * 属性注释
     */
    private String comment;

    private String columnName;

    private String jdbcType;
}
