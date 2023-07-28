package com.mz.common.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * What -- 内存池信息
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MemoryPoolVo
 * @CreateTime 2023/4/1 23:11
 */
@Data
@Accessors(chain = true)
public class MemoryPoolVo implements Serializable {

    /**
     * 内存区名称
     */
    private String name;

    /**
     * 所属内存管理者
     */
    private String manageNames;

    /**
     * 已申请内存
     */
    private Long committed;

    /**
     * 最大内存量
     */
    private Long max;

    /**
     * 已使用内存（字节）
     */
    private Long used;
}