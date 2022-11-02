package com.mz.common.mybatis.exception;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * What -- Mybatis异常处理器
 * <br>
 * Describe -- 如果时出现404的话，是不被GlobalExceptionHandler处理的
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzGlobalExceptionHandler
 * @CreateTime 2022/5/20 21:54
 */
@Slf4j
@RestControllerAdvice
public class MzMybatisExceptionHandler {

    /**
     * 结果过多异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(TooManyResultsException.class)
    public JSONObject tooManyResultsException(TooManyResultsException e) {
        String message = e.getMessage();
        log.error("结果过多异常:{}",e.getMessage());
        return JSONUtil.parseObj(String.format("{code:%d,message:'%s',timestamp:%d}", 1210, message, System.currentTimeMillis()));
    }

}
