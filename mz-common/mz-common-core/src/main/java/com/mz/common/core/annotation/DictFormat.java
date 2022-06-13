package com.mz.common.core.annotation;

import java.lang.annotation.*;

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
     * 默认值，当字典里没有对应的值时，显示的字典值
     */
    String defaultValue() default "";
}
