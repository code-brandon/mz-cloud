package com.mz.common.core.exception;

import com.mz.common.core.entity.R;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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
     * feign 异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(FeignException.class)
    public R feignException(FeignException e) {
        return R.error(e.status(),"Feign:" + e.getMessage());
    }

    /**
     * 字段 异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> map = new HashMap<>();
        bindingResult.getFieldErrors().stream().forEach(fieldError -> {
            // 返回对象的受影响字段
            String field = fieldError.getField();
            // 返回用于解析此消息的默认消息。
            String message = fieldError.getDefaultMessage();
            // 将错误信息放到MAP中
            map.put(field, message);
        });
        return R.fail(MzCodeEnum.VAILD_EXCEPTION.getCode(),MzCodeEnum.VAILD_EXCEPTION.getMsg(),map);
    }

    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(MzException.class)
    public R baseException(MzException e) {
        return R.error(MzCodeEnum.UNKNOW_EXCEPTION.getCode(),e.getMessage());
    }


}
