package com.mz.common.redis.annotation;

import com.mz.common.redis.config.MzCacheConfig;
import com.mz.common.redis.config.MzRedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * What -- redis自动配置
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: EnableMzRedisAutoConfigure
 * @CreateTime 2022/5/23 17:02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MzCacheConfig.class, MzRedisConfig.class})
public @interface EnableMzRedisAutoConfigure  {

}