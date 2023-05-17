package com.oms.api.handler;

import com.oms.api.entity.ErrorResult;
import com.oms.api.enums.ResultCode;
import com.oms.api.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(BizException.class)
    public ErrorResult bizExceptionHandler(BizException e, HttpServletRequest request) {
        return ErrorResult.fail(e.getCode(), e.getMessage());
    }

    // 拦截抛出的异常，@ResponseStatus：用来改变响应状态码
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handlerThrowable(Throwable e, HttpServletRequest request) {
        log.error("发生未知异常！原因是: ", e);
        return ErrorResult.fail(ResultCode.SYSTEM_ERROR, e);
    }

    // 参数校验异常
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorResult handleBindException(BindException e, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : e.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ErrorResult.fail(ResultCode.PARAM_IS_INVALID, errors);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResult handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        return ErrorResult.fail(ResultCode.USER_NOT_LOGGED_IN, e, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ErrorResult handleException(Exception e) throws Exception {
        //抛出AccessDeniedException异常
        ResultCode code = ResultCode.SYSTEM_ERROR;
        return ErrorResult.fail(code, e, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e;
    }
}
