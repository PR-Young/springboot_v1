package com.system.springbootv1.model;

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

/**
 * @description:
 * @author: yy 2020/01/25
 **/
@Data
public class SysRole extends BaseModel {

    private String roleName;

    private String description;
}
