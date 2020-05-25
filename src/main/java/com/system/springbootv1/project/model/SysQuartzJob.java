package com.system.springbootv1.project.model;

import com.system.springbootv1.common.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class SysQuartzJob extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名称",required = true)
    @NotEmpty(message = "任务名称不能为空")
    private String jobName;
    @ApiModelProperty(value = "任务组别",required = true)
    @NotEmpty(message = "任务组别不能为空")
    private String jobGroup;
    @ApiModelProperty(value = "目标类",required = true)
    @NotEmpty(message = "目标类不能为空")
    private String invokeTarget;
    @ApiModelProperty(value = "corn表达式",required = true)
    @NotEmpty(message = "corn表达式不能为空")
    private String cronExpression;
    @ApiModelProperty(value = "执行状态",required = true)
    @NotEmpty(message = "执行状态不能为空")
    private String conCurrent;
    @ApiModelProperty(value = "任务状态",required = true)
    private Integer status;

    private String description;

}