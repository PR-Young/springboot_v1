package com.system.springbootv1.project.model.exception;

import java.util.List;

/**
 * @description:
 * @author: yy 2020/06/19
 **/
public class Exception<T> {
    private String code;
    private String msg;
    private List<T> errors;

    public static Exception.ExceptionBuilder newInstance() {
        return new Exception.ExceptionBuilder<>();
    }

    protected Exception(Exception.ExceptionBuilder<T> builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.errors = builder.errors;
    }

    public static class ExceptionBuilder<T> {
        private String code;
        private String msg;
        private List<T> errors;

        public ExceptionBuilder() {
        }

        public Exception.ExceptionBuilder withCode(String value) {
            this.code = value;
            return this;
        }

        public Exception.ExceptionBuilder withMsg(String value) {
            this.msg = value;
            return this;
        }

        public Exception.ExceptionBuilder withErrors(List value) {
            this.errors = value;
            return this;
        }

        public Exception build() {
            return new Exception<>(this);
        }
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<T> getErrors() {
        return errors;
    }
}
