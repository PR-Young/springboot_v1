package com.system.springbootv1.common.model;

import lombok.Getter;

/**
 * @description:
 * @author: yy 2020/03/30
 **/
@Getter
public enum ResultCode {

    SUCCESS(200, "成功"),
    FAILED(500, "错误"),
    VALIDATE_FAILED(1001, "参数校验失败"),
    UNKNOWN(1000, "未知错误"),
    NOAUTH(403, "没有权限");

    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
