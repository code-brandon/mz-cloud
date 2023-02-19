package com.mz.common.log.annotation;

import com.mz.common.log.enums.BusinessType;
import com.mz.common.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * What -- Mz日志注解
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.log.annotation
 * @ClassName: MzLog
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/11 0:30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MzLog {

    /**
     * 标题
     */
    String title() default "";

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequest() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponse() default true;
}
