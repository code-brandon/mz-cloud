package com.mz.common.feign.exception;

import com.mz.common.core.entity.R;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * What --Mz feign 异常捕获
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzFeignExceptionHandler
 * @CreateTime 2022/12/6 22:50
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class MzFeignExceptionHandler {

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
}