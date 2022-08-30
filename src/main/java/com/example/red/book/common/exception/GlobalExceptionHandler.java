package com.example.red.book.common.exception;


import com.example.red.book.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @className: GlobalExceptionHandler
 * @description: 全局异常处理
 * @author: liusCoding
 * @create: 2020-05-02 12:23
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 指定出现什么异常执行这个方法
     */
    //@ExceptionHandler(Exception.class)
    //public ResultVo error(Exception e) {
    //    e.printStackTrace();
    //    return ResultVo.error().msg("服务器又耍流氓了..");
    //}


    /**
     * 自定义异常处理
     *
     * @param e
     * @return ResultVo
     */
    @ExceptionHandler(GlobalException.class)
    public CommonResult error(GlobalException e) {
        log.error("异常信息：{}", e);
        return CommonResult.failed(e.getCode(), (e.getMessage()));
    }
}
