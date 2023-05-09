package com.oms.api.handler;

import com.oms.api.entity.ErrorResult;
import com.oms.api.enums.ResultCode;
import com.oms.api.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(BizException.class)
    public ErrorResult bizExceptionHandler(BizException e, HttpServletRequest request) {
        log.error("发生业务异常！原因是: {}", e.getMessage());
        return ErrorResult.fail(e.getCode(), e.getMessage());
    }

    // 拦截抛出的异常，@ResponseStatus：用来改变响应状态码
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handlerThrowable(Throwable e, HttpServletRequest request) {
        log.error("发生未知异常！原因是: ", e);
        ErrorResult error = ErrorResult.fail(ResultCode.SYSTEM_ERROR, e);
        return error;
    }

    // 参数校验异常
    @ExceptionHandler(BindException.class)
    public ErrorResult handleBindException(BindException e, HttpServletRequest request) {
        log.error("发生参数校验异常！原因是：", e);
        ErrorResult error = ErrorResult.fail(ResultCode.PARAM_IS_INVALID, e, e.getAllErrors().get(0).getDefaultMessage());
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("发生参数校验异常！原因是：", e);
        ErrorResult error = ErrorResult.fail(ResultCode.PARAM_IS_INVALID, e, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResult handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        ErrorResult error = ErrorResult.fail(ResultCode.USER_NOT_LOGGED_IN, e, e.getMessage());
        return error;
    }
}
