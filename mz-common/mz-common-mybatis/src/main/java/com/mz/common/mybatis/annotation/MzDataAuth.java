package com.mz.common.mybatis.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * What -- 数据权限实现的注解
 * <br>
 * Describe --
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
public @interface MzDataAuth {
	String deptAlias() default "sd";
	String deptFrom() default "sys_dept";

	String deptField() default "dept_id";

	String userAlias() default "su";
	String userFrom() default "sys_user";

	String userField() default "user_id";
	boolean ignore() default false;
}