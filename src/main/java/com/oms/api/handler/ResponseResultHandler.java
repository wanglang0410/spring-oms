package com.oms.api.handler;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oms.api.common.annotation.ResponseResult;
import com.oms.api.entity.ErrorResult;
import com.oms.api.entity.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    // 标记名称
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        // 判断请求是否有包装标记
        ResponseResult responseResultAnn = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);
        return responseResultAnn == null ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.OK);
        if (body instanceof ErrorResult) {
            ErrorResult error = (ErrorResult) body;
            return Response.fail(error.getCode(), error.getMessage(), error.getData());
        } else if (body instanceof Response) {
            return body;
        } else if (body instanceof String) {
            return body;
        } else if (body instanceof IPage<?>) {
            IPage<?> bodyPage = (IPage<?>) body;
            Map<String, Object> result = new HashMap<>();
            result.put("list", bodyPage.getRecords());
            result.put("totalPage", bodyPage.getPages());
            result.put("size", bodyPage.getSize());
            result.put("page", bodyPage.getCurrent());
            result.put("totalSize", bodyPage.getTotal());
            return Response.success(result);
        }
        return Response.success(body);
    }
}
