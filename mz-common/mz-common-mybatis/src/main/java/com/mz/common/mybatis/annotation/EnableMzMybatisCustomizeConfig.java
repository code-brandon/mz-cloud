package com.mz.common.mybatis.annotation;

import com.mz.common.mybatis.config.MzMybatisAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * What -- 启用 Mz Mybatis 自定义配置
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.mybatis.annotation
 * @ClassName: EnableMzMybatisCustomizeConfig
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/1 17:26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MzMybatisAutoConfiguration.class})
public @interface EnableMzMybatisCustomizeConfig {
}
