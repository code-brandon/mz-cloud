package com.mz.common.core.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.mz.common.constant.Constant;
import com.mz.common.constant.enums.MzErrorCodeEnum;
import com.mz.common.core.entity.R;
import com.mz.common.utils.CharsetKitUtils;
import com.mz.common.utils.ConvertUtils;
import com.mz.common.utils.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;
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
@Order(1)
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
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(MzException.class)
    public R<String> mzException(MzException e) {
        String stackTraceByPn = ThrowableUtils.getStackTraceByPn(e, Constant.PACKAGE_PRE_FIX);
        log.error("业务异常:{}",stackTraceByPn);
        return R.error(MzErrorCodeEnum.UNKNOW_EXCEPTION.getCode(),e.getMessage());
    }

    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(MzRemoteException.class)
    public R<String> remoteException(MzRemoteException e) {
        String stackTraceByPn = ThrowableUtils.getStackTraceByPn(e, Constant.PACKAGE_PRE_FIX);
        log.error("远程调用异常:{}",stackTraceByPn);
        return R.error(MzErrorCodeEnum.UNKNOW_EXCEPTION.getCode(),e.getMessage());
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
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public R<Map<String,String>> methodException(Exception e) {
        Map<String, String> map = new HashMap<>(5);
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodValidException = (MethodArgumentNotValidException) e;
            methodValidException.getBindingResult().getFieldErrors().forEach(fieldError -> {
                // 返回对象的受影响字段
                String field = fieldError.getField();
                // 返回用于解析此消息的默认消息。
                String message = fieldError.getDefaultMessage();
                // 将错误信息放到MAP中
                map.put(field, message);
            });
        }else if (e instanceof ConstraintViolationException) {
            // BeanValidation GET simple param
            ConstraintViolationException validator = (ConstraintViolationException) e;
            validator.getConstraintViolations().forEach(fieldError->{
                // 返回对象的受影响字段
                String field = fieldError.getPropertyPath().toString();
                // 返回用于解析此消息的默认消息。
                String message = fieldError.getMessage();
                // 将错误信息放到MAP中
                map.put(field, message);
            });
        } else if (e instanceof BindException) {
            // BeanValidation GET object param
            BindException bindException = (BindException) e;
            bindException.getFieldErrors().forEach(fieldError -> {
                // 返回对象的受影响字段
                String field = fieldError.getField();
                // 返回用于解析此消息的默认消息。
                String message = fieldError.getDefaultMessage();
                // 将错误信息放到MAP中
                map.put(field, message);
            });
        }
        log.error("字段校验异常：{}",e.getMessage());
        return R.fail(MzErrorCodeEnum.VAILD_EXCEPTION.getCode(), MzErrorCodeEnum.VAILD_EXCEPTION.getMsg(),map);
    }


}
