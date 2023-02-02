package com.mz.common.core.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.mz.common.constant.enums.MzErrorCodeEnum;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 业务断言类，不满足断言条件的都抛{@link MzException}
 * (默认{@link MzException#getCode()} ()}  getCode}错误码为{@link MzErrorCodeEnum#PARAM_ERROR}中的{@code code}值,即'403').
 * <br/>
 * <br/>
 * 各个系统可通过{@link #setDefaultErrorCode}为其指定独有的默认错误码,方便问题溯源.
 * 如微服务系统中为sys服务设置默认错误码,则可以在main方法中进行如下设置: {@code BizAssert.setDefaultErrorCode("SYSOO1")},
 * 那么如果使用该类对业务进行逻辑断言时,不满足期望时业务异常({@link MzException})中的错误码都将会是SYS001.
 * <br/>
 * <br/>
 * 若存在不方便使用断言的情况,需手动抛业务异常,也可使用{@link #newException(String)}的简单工厂来生成业务异常,
 * 如{@code throw BizAssert.newException("该用户名已存在")},
 * 那么该业务异常的错误码也会使用全局设置的默认错误码,避免手动为业务异常赋值错误码.
 *
 * @author 原作者：meilin.huang
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime 2023/2/1 21:53
 * @see MzException
 * @see MzErrorCodeEnum
 */
public final class MzAssert {

    public static int defaultErrorCode = MzErrorCodeEnum.PARAM_ERROR.getCode();

    /**
     * 设置默认错误消息，可根据不同模块设置不同错误码，方便问题溯源
     *
     * @param errorCode 默认错误码
     */
    public static void setDefaultErrorCode(int errorCode) {
        defaultErrorCode = errorCode;
    }

    private MzAssert() {
    }

    /**
     * 断言对象不为空
     *
     * @param object 对象
     * @param msg    不满足断言的异常信息
     */
    public static void notNull(Object object, String msg) {
        if (object == null) {
            throw newException(msg);
        }
    }

    /**
     * 断言对象不为空
     *
     * @param object   对象
     * @param supplier 错误消息供应器
     */
    public static void notNull(Object object, Supplier<String> supplier) {
        if (object == null) {
            throw newException(supplier.get());
        }
    }

    /**
     * 断言对象不能为空
     *
     * @param object    对象
     * @param errorEnum 错误枚举值
     */
    public static void notNull(Object object, MzErrorCodeEnum errorEnum, Object... params) {
        if (object == null) {
            throw newException(errorEnum, params);
        }
    }

    /**
     * 断言对象为空
     *
     * @param object 对象
     * @param msg    不满足断言的异常信息
     */
    public static void isNull(Object object, String msg) {
        isTrue(object == null, msg);
    }

    /**
     * 断言对象为空
     *
     * @param object   obj
     * @param supplier 错误消息提供器
     */
    public static void isNull(Object object, Supplier<String> supplier) {
        isTrue(object == null, supplier);
    }

    /**
     * 断言对象为空
     *
     * @param object    obj
     * @param errorEnum 错误枚举
     */
    public static void isNull(Object object, MzErrorCodeEnum errorEnum, Object... params) {
        isTrue(object == null, errorEnum, params);
    }


    /**
     * 断言字符串不为空
     *
     * @param str 字符串
     * @param msg 不满足断言的异常信息
     */
    public static void notEmpty(String str, String msg) {
        isTrue(!StringUtils.isEmpty(str), msg);
    }

    /**
     * 断言字符串不为空
     *
     * @param str      字符串
     * @param supplier 错误消息供应器
     */
    public static void notEmpty(String str, Supplier<String> supplier) {
        isTrue(!StringUtils.isEmpty(str), supplier);
    }

    /**
     * 断言字符串不为空
     *
     * @param str       字符串
     * @param errorEnum 错误枚举
     */
    public static void notEmpty(String str, MzErrorCodeEnum errorEnum, Object... params) {
        isTrue(!StringUtils.isEmpty(str), errorEnum, params);
    }

    /**
     * 断言集合不为空
     *
     * @param collection 集合
     * @param msg        不满足断言的异常信息
     */
    public static void notEmpty(Collection<?> collection, String msg) {
        isTrue(CollectionUtil.isNotEmpty(collection), msg);
    }

    /**
     * 断言集合不为空
     *
     * @param collection 集合
     * @param supplier   不满足断言的异常信息提供者
     */
    public static void notEmpty(Collection<?> collection, Supplier<String> supplier) {
        isTrue(CollectionUtil.isNotEmpty(collection), supplier);
    }

    /**
     * 断言集合不为空
     *
     * @param collection 集合
     * @param errorEnum  错误枚举
     */
    public static void notEmpty(Collection<?> collection, MzErrorCodeEnum errorEnum, Object... params) {
        isTrue(CollectionUtil.isNotEmpty(collection), errorEnum, params);
    }

    /**
     * 断言map不为空
     *
     * @param map map
     * @param msg 不满足断言的异常信息
     */
    public static void notEmpty(Map<?, ?> map, String msg) {
        isTrue(!MapUtil.isEmpty(map), msg);
    }

    /**
     * 断言map不为空
     *
     * @param map      map
     * @param supplier 错误消息供应器
     */
    public static void notEmpty(Map<?, ?> map, Supplier<String> supplier) {
        isTrue(!MapUtil.isEmpty(map), supplier);
    }

    /**
     * 断言map不为空
     *
     * @param map       map
     * @param errorEnum 错误枚举
     */
    public static void notEmpty(Map<?, ?> map, MzErrorCodeEnum errorEnum, Object... params) {
        isTrue(!MapUtil.isEmpty(map), errorEnum, params);
    }

    /**
     * 断言集合为空
     *
     * @param collection 集合
     * @param msg        不满足断言的异常信息
     */
    public static void empty(Collection<?> collection, String msg) {
        isTrue(CollectionUtil.isEmpty(collection), msg);
    }

    /**
     * 断言集合为空
     *
     * @param collection 集合
     * @param supplier   不满足断言的异常信息提供器
     */
    public static void empty(Collection<?> collection, Supplier<String> supplier) {
        isTrue(CollectionUtil.isEmpty(collection), supplier);
    }

    /**
     * 断言集合为空
     *
     * @param collection 集合
     * @param errorEnum  错误枚举
     */
    public static void empty(Collection<?> collection, MzErrorCodeEnum errorEnum, Object... params) {
        isTrue(CollectionUtil.isEmpty(collection), errorEnum, params);
    }

    /**
     * 断言两个对象必须相等
     *
     * @param o1  对象1
     * @param o2  对象2
     * @param msg 错误消息
     */
    public static void equals(Object o1, Object o2, String msg) {
        isTrue(Objects.equals(o1, o2), msg);
    }

    /**
     * 断言两个对象必须相等
     *
     * @param o1          对象1
     * @param o2          对象2
     * @param msgSupplier 错误消息提供器
     */
    public static void equals(Object o1, Object o2, Supplier<String> msgSupplier) {
        isTrue(Objects.equals(o1, o2), msgSupplier);
    }

    /**
     * 断言两个对象必须相等
     *
     * @param o1        对象1
     * @param o2        对象2
     * @param errorEnum 错误枚举
     */
    public static void equals(Object o1, Object o2, MzErrorCodeEnum errorEnum, Object... params) {
        isTrue(Objects.equals(o1, o2), errorEnum, params);
    }


    /**
     * 断言两个对象不相等
     *
     * @param o1  对象1
     * @param o2  对象2
     * @param msg 错误消息
     */
    public static void notEquals(Object o1, Object o2, String msg) {
        isTrue(!Objects.equals(o1, o2), msg);
    }

    /**
     * 断言两个对象不相等
     *
     * @param o1          对象1
     * @param o2          对象2
     * @param msgSupplier 错误消息提供器
     */
    public static void notEquals(Object o1, Object o2, Supplier<String> msgSupplier) {
        isTrue(!Objects.equals(o1, o2), msgSupplier);
    }

    /**
     * 断言两个对象不相等
     *
     * @param o1        对象1
     * @param o2        对象2
     * @param errorEnum 错误枚举
     */
    public static void notEquals(Object o1, Object o2, MzErrorCodeEnum errorEnum, Object... params) {
        isTrue(!Objects.equals(o1, o2), errorEnum, params);
    }

    /**
     * 断言对象数组包含指定元素
     *
     * @param o1   对象1
     * @param objs 对象2
     * @param msg  错误消息
     */
    public static <T> void contains(T o1, T[] objs, String msg) {
        isTrue(ArrayUtils.contains(objs, o1), msg);
    }

    /**
     * 断言对象数组包含指定元素
     *
     * @param o1          对象1
     * @param objs        对象2
     * @param msgSupplier 错误消息提供器
     */
    public static <T> void contains(T o1, T[] objs, Supplier<String> msgSupplier) {
        isTrue(ArrayUtils.contains(objs, o1), msgSupplier);
    }

    /**
     * 断言对象数组包含指定元素
     *
     * @param o1        对象1
     * @param objs      对象2
     * @param errorEnum 错误枚举
     */
    public static <T> void contains(T o1, T[] objs, MzErrorCodeEnum errorEnum, Object... params) {
        isTrue(ArrayUtils.contains(objs, o1), errorEnum, params);
    }


    /**
     * 断言一个boolean表达式为true
     *
     * @param expression boolean表达式
     * @param message    不满足断言的异常信息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw newException(message);
        }
    }

    /**
     * 断言一个boolean表达式为true，用于需要大量拼接字符串以及一些其他操作等
     *
     * @param expression boolean表达式
     * @param supplier   msg生产者
     */
    public static void isTrue(boolean expression, Supplier<String> supplier) {
        if (!expression) {
            throw newException(supplier.get());
        }
    }

    /**
     * 断言一个boolean表达式为true，用于需要大量拼接字符串以及一些其他操作等
     *
     * @param expression boolean表达式
     * @param errorEnum  错误枚举
     */
    public static void isTrue(boolean expression, MzErrorCodeEnum errorEnum, Object... params) {
        if (!expression) {
            throw newException(errorEnum, params);
        }
    }

    /**
     * 创建业务运行时异常对象
     *
     * @param msg 异常信息
     * @return 异常
     */
    public static MzException newException(String msg) {
        return new MzException(msg, defaultErrorCode);
    }

    /**
     * 创建业务运行时异常对象，并将异常的message填充进参数msg
     *
     * @param msg 异常信息，可使用 '%s' 接受异常消息
     * @param e   包装异常
     * @return 异常
     */
    public static MzException newException(String msg, Exception e) {
        return new MzException(String.format(msg, e.getMessage()), defaultErrorCode);
    }

    /**
     * 创建业务运行时异常对象
     *
     * @param errorEnum 错误枚举
     * @return 异常
     */
    public static MzException newException(MzErrorCodeEnum errorEnum, Object... params) {
        return new MzException(errorEnum, params);
    }
}
