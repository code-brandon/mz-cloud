package com.mz.common.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * What -- 编译信息
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: CompilationVo
 * @CreateTime 2023/4/1 20:30
 */
@Data
@Accessors(chain = true)
public class CompilationVo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 即时（JIT）编译器名称
     */
    private String name;

    /**
     * 总编译时间（毫秒）
     */
    private Long totalTime;

    /**
     * JVM是否支持对编译时间的监视
     */
    private Boolean isSupport;
}