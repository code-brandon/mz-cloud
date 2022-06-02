package com.mz.common.security.annotation;

import java.lang.annotation.*;

/**
 * What -- 服务调用鉴权注解
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.security.annotation
 * @ClassName: ignore
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/2 16:47
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ignore {
    /**
     * 是否AOP统一处理，默认不进行鉴权
     * @return false, true
     */
    boolean value() default true;
}
