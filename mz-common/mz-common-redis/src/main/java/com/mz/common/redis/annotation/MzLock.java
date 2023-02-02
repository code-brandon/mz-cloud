package com.mz.common.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * What -- 全局分布式锁注解
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzLock
 * @CreateTime 2022/10/28 20:55
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MzLock {
    /**
     * 锁的名称: 支持spel表达式 eg:#tenantDo.id
     * 默认为: mz_cloud_lock:global: + lockKey
     */
    String lockKey();


    /**
     * 如果设置了 存储Key则使用存储Key ，默认全局与局部存储Key失效
     * @return
     */
    String storageKey() default "";
 
    /**
     * 是否使用尝试锁。
     */
    boolean tryLock() default true;
    /**
     * 最长等待时间。默认3秒 （等待获取锁时间）
     * 该字段只有当tryLock()返回true才有效。
     */
    long waitTime() default 3L;

    /**
     * 获取锁失败提示
     * @return
     */
    String message() default "获取锁失败";
 
    /**
     * 锁超时时间。默认5秒 （自动释放锁时间）
     * 如果tryLock为false，且leaseTime设置为0及以下，会变成lock()
     */
    long leaseTime() default 5L;
 
    /**
     * 时间单位。默认为秒。
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
 
}