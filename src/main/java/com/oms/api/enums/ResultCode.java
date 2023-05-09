package com.oms.api.enums;

public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(200, "成功"),
    /* 参数错误 */
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    USER_NOT_LOGGED_IN(401, "用户未登录"),
    USER_NOT_FORBIDDEN(403, "用户权限不足"),
    USER_LOGIN_ERROR(404, "账号不存在或密码错误"),
    SYSTEM_ERROR(10000, "系统异常，请稍后重试");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
