package com.mz.common.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * What -- JVM垃圾回收信息
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: GarbageCollectorVo
 * @CreateTime 2023/4/1 20:31
 */
@Data
@Accessors(chain = true)
public class GarbageCollectorVo implements Serializable {

    /**
     * GC回收次数
     */
    private Long count;

    /**
     * GC回收耗时
     */
    private Long time;
}