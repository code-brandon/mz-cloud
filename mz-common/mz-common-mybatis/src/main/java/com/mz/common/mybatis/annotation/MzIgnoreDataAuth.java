package com.mz.common.mybatis.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * What -- 数据权限 全局忽略注解
 * <br>
 * Describe -- 建议标记在controller层 进行后续在dao层的数据权限忽略
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzDataAuthEnum
 * @CreateTime 2023/2/23 20:17
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MzIgnoreDataAuth {

	/**
	 * treu 表示当前线程下的数据权限全部忽略
	 * @return
	 */
	boolean value() default true;
}