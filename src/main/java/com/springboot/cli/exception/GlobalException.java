package com.springboot.cli.exception;


import com.springboot.cli.response.ErrorCode;
import com.springboot.cli.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 为了使调用接口报不同类型的错误
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("system server error", e);
        return new Result(ErrorCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(ExamException.class)
    public Result handleBizException(ExamException e) {
        log.error("biz exception occur", e);
        return new Result(e.getCode(), e.getMessage());
    }
}
