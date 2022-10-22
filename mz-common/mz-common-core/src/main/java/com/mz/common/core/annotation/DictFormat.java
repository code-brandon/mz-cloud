package com.mz.common.core.annotation;

import java.lang.annotation.*;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: DictFormat
 * @CreateTime 2022/10/15 22:21
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictFormat {

    /**
     * 字典名称编码, 默认为当前属性的名称
     */
    String dictKey() default "";

    /**
     * 字典类型，必须指定
     */
    String dictType() default "";

    /**
     * 产生新的字段用于字典翻译，空时 修改原属性名的值
     */
    String newField() default "";

    /**
     * 默认值，当字典里没有对应的值时，显示的字典值
     */
    String defaultValue() default "";
}
