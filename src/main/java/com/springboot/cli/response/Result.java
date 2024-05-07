package com.springboot.cli.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public static Result fail(ErrorCode errorCode) {
        Result result = new Result(errorCode);
        return result;
    }

    public Result success(T data){
        Result result = new Result(ErrorCode.OK);
        result.setData(data);
        return result;
    }
    public static Result success(){
        Result result = new Result(ErrorCode.OK);
        return result;
    }
}
