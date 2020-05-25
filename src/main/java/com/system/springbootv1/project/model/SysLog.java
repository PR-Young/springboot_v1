package com.system.springbootv1.project.model;

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: yy 2020/01/27
 **/
@Data
public class SysLog extends BaseModel implements Serializable {

    private String hostIp;
    private String userName;
    private String url;
    private String operParams;
    private String notes;
    /** 请求方式 */
    private String requestMethod;
}
