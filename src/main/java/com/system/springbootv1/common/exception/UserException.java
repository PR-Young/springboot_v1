package com.system.springbootv1.common.exception;

/**
 * @description:
 * @author: yy 2020/05/09
 **/
public class UserException extends RuntimeException {

    private int code;

    private String message;

    public UserException() {
        super();
    }

    public UserException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public UserException(String message, Throwable e) {
        super(message, e);
    }
}
