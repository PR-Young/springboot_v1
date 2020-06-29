package com.system.springbootv1.project.model;

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

@Data
public class SysTemplate extends BaseModel {
    private String name;
    private String subject;
    private String text;
    private String html;
    private String param;
}