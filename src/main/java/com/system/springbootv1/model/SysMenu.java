package com.system.springbootv1.model;

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
@Data
public class SysMenu extends BaseModel {

    private String name;

    private String description;

    private String url;

    private Integer isBlank;

    private String pId;

    private String icon;

    private String perms;

    private Integer type;

    private Integer order;

    private Integer visible;

    private Integer childCount;
}
