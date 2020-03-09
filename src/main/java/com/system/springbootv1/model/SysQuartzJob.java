package com.system.springbootv1.model;

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysQuartzJob extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String jobName;

    private String jobGroup;

    private String invokeTarget;

    private String cronExpression;

    private String misfirePolicy;

    private String conCurrent;

    private Integer status;

}