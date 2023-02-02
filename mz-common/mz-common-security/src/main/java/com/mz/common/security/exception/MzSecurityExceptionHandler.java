package com.mz.common.security.exception;

import com.mz.common.constant.enums.MzErrorCodeEnum;
import com.mz.common.core.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * What -- Mz  安全异常处理程序
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzSecurityExceptionHandler
 * @CreateTime 2022/12/6 21:28
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class MzSecurityExceptionHandler {

    /**
     * AccessDeniedException 异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public R<Boolean> accessDeniedException(AccessDeniedException e) {
        log.error("访问被拒绝异常:{}",e.getMessage());
        return R.error(MzErrorCodeEnum.OAUTH_ACCESS_EXCEPTION.getCode(),e.getMessage());
    }
}