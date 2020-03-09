package com.system.springbootv1.model;

import com.system.springbootv1.common.model.BaseModel;
import lombok.Data;

/**
 * @description:
 * @author: yy 2020/01/27
 **/
@Data
public class SysLog extends BaseModel {

    private String hostIp;
    private String userName;
    private String url;
    private String notes;
}
