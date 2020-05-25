package com.system.springbootv1.project.model;

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: yy 2020/01/25
 **/
@Data
public class SysRole extends BaseModel implements Serializable {

    private String roleName;

    private String description;

    /** 菜单组 */
    private String[] menuIds;

    private String[] userIds;
}
