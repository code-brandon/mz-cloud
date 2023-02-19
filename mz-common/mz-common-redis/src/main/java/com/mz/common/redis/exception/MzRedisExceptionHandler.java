package com.mz.common.redis.exception;

import com.mz.common.constant.Constant;
import com.mz.common.utils.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.redis.exception
 * @ClassName: MzRedisExceptionHandler
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/12/23 18:42
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 10000)
@RestControllerAdvice
public class MzRedisExceptionHandler {
    /**
     * Mz Redis 锁异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MzRedisException.class)
    public String baseException(MzRedisException e) {
        log.error("redis锁异常：{}", ThrowableUtils.getStackTraceByPn(e, Constant.PACKAGE_PRE_FIX));
        return String.format("{code:%d,message:'%s',timestamp:%d}", e.getCode(), "锁异常", System.currentTimeMillis());
    }
}
