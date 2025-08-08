package com.example.crud.exception;

import com.example.crud.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 捕获所有未处理的异常
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("【全局异常】捕获到未处理异常：", e);
        return Result.error("服务器开小差了，请稍后再试");
    }

    // 你也可以定义其他特定类型的异常处理
    // @ExceptionHandler(NullPointerException.class)
    // public Result<?> handleNullPointerException(NullPointerException e) {
    //     log.error("空指针异常：", e);
    //
}
