package com.mz.common.core.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.mz.common.core.entity.R;
import com.mz.common.core.utils.CharsetKitUtils;
import com.mz.common.core.utils.ConvertUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
@Slf4j
@RestControllerAdvice
public class MzGlobalExceptionHandler {

    /**
     * 处理非业务异常，比如 连接异常，空指针异常...
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R<String> runtimeException(Exception e) {
        e.printStackTrace();
        String message = e.getMessage();
        log.error("未知异常:{}",StringUtils.isEmpty(message) ? ConvertUtils.str(e.getCause().getMessage(), CharsetKitUtils.CHARSET_UTF_8) : message);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), StringUtils.isEmpty(message) ? ConvertUtils.str(e.getCause().getMessage(), CharsetKitUtils.CHARSET_UTF_8) : message,"未知异常");
    }

    /**
     * 基础异常
     */
    @ExceptionHandler(MzBaseException.class)
    public R baseException(MzBaseException e) {
        log.error("基础异常:{}",e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * feign 异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(FeignException.class)
    public R<String> feignException(FeignException e) {
        log.error("Feign异常:{}",e.getMessage());
        return R.error(e.status(),e.getMessage(),"Feign异常");
    }

    @ExceptionHandler(MismatchedInputException.class)
    public R<String> mismatchedInputException(MismatchedInputException e) {
        log.error("jackson序列化异常:{}",e.getMessage());
        return R.error(e.getMessage(),"jackson序列化异常");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        Boolean isIncludeBody= Optional.ofNullable(message).map(me -> me.contains("Required request body is missing")).orElse(false);
        if (Boolean.TRUE.equals(isIncludeBody)) {
            message = "缺少必需的请求正文";
        }
        log.error("HTTP请求异常:{}",e.getMessage());
        return R.error(message, "HTTP请求异常");
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public R<String> unexpectedTypeException(UnexpectedTypeException e) {
        String message = e.getMessage();
        log.error("类型转换异常:{}",message);
        return R.error(message, "类型转换异常:");
    }

    /**
     * 字段 异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Map<String,String>> methodException(MethodArgumentNotValidException e) {
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
        log.error("自定义异常:{}",e.getMessage());
        return R.error(MzCodeEnum.UNKNOW_EXCEPTION.getCode(),e.getMessage());
    }


}
