package com.oms.api.entity;

import com.oms.api.enums.ResultCode;
import lombok.Data;

@Data
public class ErrorResult {
    private Integer code;

    private String message;

    private String exception;

    public static ErrorResult fail(ResultCode resultCode, Throwable e, String message) {
        ErrorResult errorResult = ErrorResult.fail(resultCode, e);
        errorResult.setMessage(message);
        return errorResult;
    }

    public static ErrorResult fail(ResultCode resultCode, Throwable e) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(resultCode.code());
        errorResult.setMessage(resultCode.message());
        errorResult.setException(e.getClass().getName());
        return errorResult;
    }

    public static ErrorResult fail(Integer code, String message) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(code);
        errorResult.setMessage(message);
        return errorResult;
    }
}