package com.mz.common.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * What -- JVM运行时信息
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: RuntimeVo
 * @CreateTime 2023/4/1 23:30
 */
@Data
@Accessors(chain = true)
public class RuntimeVo implements Serializable {

    /**
     * 主机
     */
    private String host;

    /**
     * JVM
     */
    private String jvm;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK 路径
     */
    private String home;

    /**
     * 当前JVM参数信息
     */
    private List<String> args;

    /**
     * JVM开始启动的时间（毫秒）
     */
    private Long startTime;

    /**
     * JSON格式化后系统详细参数
     */
    private List<Map<String,Object>> properties;
}