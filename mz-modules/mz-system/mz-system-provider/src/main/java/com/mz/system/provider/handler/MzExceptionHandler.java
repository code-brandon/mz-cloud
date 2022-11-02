package com.mz.system.provider.handler;

import com.mz.common.core.entity.R;
import com.mz.common.redis.exception.MzRedisLockException;
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
public class MzExceptionHandler {

    /**
     * Mz Redis 锁异常
     * @param e
     * @return
     */
    @ExceptionHandler(MzRedisLockException.class)
    public R baseException(MzRedisLockException e) {
        return R.error(e.getCode(),e.getMsg());
    }


}
