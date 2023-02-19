package com.mz.common.constant.enums;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.utils.enums
 * @ClassName: ValueEnum
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/3 12:34
 */
public interface KeyValueEnum<K,V> extends ValueEnum<V>{

    /**
     * 获取枚举键
     * @return
     */
    K getKey();

}
