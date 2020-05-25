package com.system.springbootv1.project.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: yy 2020/01/25
 **/
@Data
public class SysPermission implements Serializable {

    private String id;
    private String roleId;
    private String menuId;
    private String perms;
}
