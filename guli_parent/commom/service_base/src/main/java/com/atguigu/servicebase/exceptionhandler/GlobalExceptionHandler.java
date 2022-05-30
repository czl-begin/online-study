package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.ExceptionUtils.ExceptionUtil;
import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/7/5 15:41
 */
/*@RestControllerAdvice*/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 统一异常处理类
     */
        @ExceptionHandler(Exception.class)
        @ResponseBody
        public R error(Exception e){
            e.printStackTrace();
            return R.error().message("执行了全局异常处理");
        }
    /**
     * 特定异常处理类
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }
    /**
     * 自定义异常处理类
     */
    @ExceptionHandler(GuliExcepption.class)
    @ResponseBody
    public R error(GuliExcepption e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
    }
