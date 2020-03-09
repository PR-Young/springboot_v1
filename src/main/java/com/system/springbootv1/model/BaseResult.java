package com.system.springbootv1.model;

import lombok.Data;

import java.util.HashMap;

/**
 * @description:
 * @author: yy 2020/01/23
 **/
@Data
public class BaseResult extends HashMap<String, Object> {
    public BaseResult() {
    }

    public static BaseResult error(int code, String message) {
        BaseResult result = new BaseResult();
        result.put("code", code);
        result.put("msg", message);
        return result;
    }

    public static BaseResult success(int code, String message) {
        BaseResult result = new BaseResult();
        result.put("code", code);
        result.put("msg", message);
        return result;
    }

    public static BaseResult error() {
        return error(1, "操作失败");
    }

    public static BaseResult error(String msg) {
        return error(500, msg);
    }

    public static BaseResult success(String msg) {
        BaseResult json = new BaseResult();
        json.put("msg", msg);
        json.put("code", 200);
        return json;
    }

    public static BaseResult success() {
        return BaseResult.success("操作成功");
    }

    @Override
    public BaseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static BaseResult successData(int code, Object value) {
        BaseResult json = new BaseResult();
        json.put("code", code);
        json.put("data", value);
        return json;
    }
}
