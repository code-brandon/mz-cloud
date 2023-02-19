package com.mz.common.utils.enums;

import com.mz.common.constant.enums.KeyValueEnum;
import com.mz.common.constant.enums.ValueEnum;
import com.mz.common.utils.MzUtils;

/**
 * What -- 常用枚举工具类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: EnumUtils
 * @CreateTime 2023/2/3 12:40
 */
public final class EnumUtils {

    /**
     * 判断枚举值是否存在于指定枚举数组中
     *
     * @param enums 枚举数组
     * @param value 枚举值
     * @return 是否存在
     */
    public static <T> boolean isExist(ValueEnum<T>[] enums, T value) {
        if (MzUtils.isEmpty(value)) {
            return false;
        }
        for (ValueEnum<T> e : enums) {
            if (value.equals(e.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断枚举值是否存与指定枚举类中
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <E>       枚举类型
     * @param <V>       值类型
     * @return true：存在
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<V>>, V> boolean isExist(Class<E> enumClass, V value) {
        if (MzUtils.isEmpty(value)) {
            return false;
        }
        for (Enum<? extends ValueEnum<V>> e : enumClass.getEnumConstants()) {
            if (((ValueEnum<V>) e).getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据枚举值获取其对应的名字
     *
     * @param enums 枚举列表
     * @param value 枚举值
     * @return 枚举名称
     */
    public static <K,V> K getNameByValue(KeyValueEnum<K,V>[] enums, V value) {
        if (MzUtils.isEmpty(value)) {
            return null;
        }
        for (KeyValueEnum<K,V> e : enums) {
            if (value.equals(e.getValue())) {
                return e.getKey();
            }
        }
        return null;
    }

    /**
     * 根据枚举名称获取对应的枚举值
     *
     * @param enums 枚举列表
     * @param key  枚举名
     * @return 枚举值
     */
    public static <K,V> V getValueByKey(KeyValueEnum<K,V>[] enums, K key) {
        if (MzUtils.isEmpty(key)) {
            return null;
        }
        for (KeyValueEnum<K,V> e : enums) {
            if (key.equals(e.getKey())) {
                return e.getValue();
            }
        }
        return null;
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enums 枚举列表
     * @return 枚举对象
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<V>>, V> E getEnumByValue(E[] enums, V value) {
        if (MzUtils.isEmpty(value)) {
            return null;
        }
        for (E e : enums) {
            if (((ValueEnum<V>) e).getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enumClass 枚举class
     * @return 枚举对象
     */
    public static <E extends Enum<? extends ValueEnum<V>>, V> E getEnumByValue(Class<E> enumClass, V value) {
        return getEnumByValue(enumClass.getEnumConstants(), value);
    }
}