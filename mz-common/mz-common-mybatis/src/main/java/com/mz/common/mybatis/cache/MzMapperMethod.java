package com.mz.common.mybatis.cache;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class MzMapperMethod {

    /**
     * 方法名
     */
    private String name;

    /**
     * 参数类型
     */
    private Class<?>[] types;

    /**
     * 返回类型
     */
    private Class<?> returnType;

    private Method method;

}