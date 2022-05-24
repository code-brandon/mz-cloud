package com.mz.common.core.exception;

import com.mz.common.core.entity.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * What -- 全局异常处理器(处理业务异常)
 * <br>
 * Describe -- 如果时出现404的话，是不被GlobalExceptionHandler处理的
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzGlobalExceptionHandler
 * @CreateTime 2022/5/20 21:54
 */
@RestControllerAdvice
public class MzGlobalExceptionHandler {

    /**
     * 处理非业务异常，比如 连接异常，空指针异常...
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R runtimeException(Exception e) {
        e.printStackTrace();
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * 基础异常
     */
    @ExceptionHandler(MzBaseException.class)
    public R baseException(MzBaseException e) {
        return R.error(e.getMessage());
    }

    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(MzException.class)
    public R baseException(MzException e) {
        return R.error(e.getMessage());
    }


}
