package com.oms.api.entity;

import com.oms.api.enums.ResultCode;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Response implements Serializable {

    private Integer code;

    private String message;

    private Object data;

    private Response() {

    }

    public Response(ResultCode resultCode, Object data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    // 返回成功
    public static Response success() {
        Response result = new Response();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    // 返回成功
    public static Response success(Object data) {
        Response result = new Response();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    // 返回失败
    public static Response fail(Integer code, String message) {
        Response result = new Response();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static Response fail(Integer code, String message, Map<String, Object> data) {
        Response result = new Response();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    // 返回失败
    public static Response fail(ResultCode resultCode) {
        Response result = new Response();
        result.setResultCode(resultCode);
        return result;
    }
}