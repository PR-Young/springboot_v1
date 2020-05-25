package com.system.springbootv1.project.model;

import com.system.springbootv1.common.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
@Data
public class SysMenu extends BaseModel implements Serializable {

    @ApiModelProperty(value = "菜单名称",required = true)
    @NotEmpty(message = "名称不能为空")
    private String name;

    private String description;

    @ApiModelProperty(value = "菜单地址",required = true)
    @NotEmpty(message = "url不能为空")
    private String url;

    private String path;

    private Integer isBlank;

    @ApiModelProperty(value = "父菜单",required = true)
    @NotEmpty(message = "父菜单不能为空")
    private String pId;

    private String icon;

    @ApiModelProperty(value = "菜单权限",required = true)
    @NotEmpty(message = "权限不能为空")
    private String perms;

    private Integer type;

    private Integer order;

    private Integer visible;

    private Integer childCount;

    /** 子菜单 */
    private List<SysMenu> children = new ArrayList<SysMenu>();
}
