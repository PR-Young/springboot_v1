package com.system.springbootv1.project.model;

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

@Data
public class SysProperties extends BaseModel {
    private String alias;
    private String description;
    private String group;
    private String name;
    private String value;
}