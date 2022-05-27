package com.mz.common.swagger.annotation;

import com.mz.common.swagger.config.Swagger3Config;
import com.mz.common.swagger.config.SwaggerProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * What -- 自定义Swagger3Config自动配置注解
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: EnableMzSwagger3Config
 * @CreateTime 2022/5/24 16:26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerProperties.class,Swagger3Config.class})
public @interface EnableMzSwagger3Config{
}