package com.mz.common.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * What -- 堆内存信息
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MemoryVo
 * @CreateTime 2023/4/1 20:34
 */
@Data
@Accessors(chain = true)
public class MemoryVo implements Serializable {

    /**
     * 已申请的堆内存
     */
    private Long committed;

    /**
     * JVM初始化堆占用的内存总量
     */
    private Long init;

    /**
     * JVM提供可用于内存管理的最大内存量
     */
    private Long max;

    /**
     * 内存区已使用空间大小（字节）
     */
    private Long used;

    /**
     * 已申请的非堆内存大小
     */
    private Long nonCommitted;

    /**
     * JVM初始化非堆区占用的内存总量
     */
    private Long nonInit;

    /**
     * JVM提供可用于非堆内存区管理的最大内存量
     */
    private Long nonMax;

    /**
     * JVM非堆内存区已使用空间大小（字节）
     */
    private Long nonUsed;
}