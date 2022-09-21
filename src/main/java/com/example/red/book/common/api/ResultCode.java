package com.example.red.book.common.api;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),

    //用户相关
    USER_NOT_FOUND(20001, "用户不存在"),
    PASSWORD_WRONG(20002, "密码错误"),
    USER_EXITS(20003, "用户已存在"),
    PASSWORD_NOT_SAME(20004, "密码不一致"),
    GET_STS_TOKEN_ERROR(20100, "获取stsToken失败"),
    UPLOAD_ERROR(20101, "上传文件失败"),
    FILE_EMPTY(20102, "文件为空");


    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
