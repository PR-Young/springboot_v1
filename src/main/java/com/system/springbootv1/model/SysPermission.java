package com.system.springbootv1.model;

import lombok.Data;

/**
 * @description:
 * @author: yy 2020/01/25
 **/
@Data
public class SysPermission {

    private String id;
    private String roleId;
    private String menuId;
    private String perms;
}
