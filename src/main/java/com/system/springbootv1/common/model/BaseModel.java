package com.system.springbootv1.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/01/22
 **/
@Data
public class BaseModel implements Serializable {
    private String Id;
    private String creator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String modifier;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifyTime;

    /** =============================查询参数================================= */
    /** 开始时间 */
    @JsonIgnore
    private String beginTime;
    /** 结束时间 */
    @JsonIgnore
    private String endTime;
    /** 请求参数 */
    private Map<String, Object> params;
}
