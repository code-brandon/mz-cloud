package com.mz.common.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * What -- 操作系统信息
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: SystemVo
 * @CreateTime 2023/4/1 23:28
 */
@Data
@Accessors(chain = true)
public class SystemVo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 操作系统名称
     */
    private String name;

    /**
     * 操作系统进程数量
     */
    private Integer processCount;

    /**
     * 操作系统架构
     */
    private String osArchName;

    /**
     * 操作系统负载平均值
     */
    private Double loadAverage;

    /**
     * 操作系统版本号
     */
    private String version;
}