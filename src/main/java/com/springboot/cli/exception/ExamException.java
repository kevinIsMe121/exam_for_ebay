package com.springboot.cli.exception;


import com.springboot.cli.response.ErrorCode;

public class ExamException extends RuntimeException {
    private int code;
    private String message;


    public ExamException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
