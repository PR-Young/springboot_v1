package com.system.springbootv1.model;

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

/**
 * @description:
 * @author: yy 2020/01/22
 **/
@Data
public class SysUser extends BaseModel {

    private String account;
    private String userName;
    private String password;

    public SysUser() {
    }

}
