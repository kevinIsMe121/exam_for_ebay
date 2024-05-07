package com.springboot.cli.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    OK(0, "成功"),
    NO_PERMISSION(403, "无权限"),
    NO_LOGIN(404,"没有登录，请先登录"),
    SYSTEM_ERROR(500, "系统异常")
    ;
    private int code;
    private String msg;
}
