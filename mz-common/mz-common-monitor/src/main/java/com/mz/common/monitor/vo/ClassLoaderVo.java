package com.mz.common.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * What -- 类加载数据
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: ClassLoaderVo
 * @CreateTime 2023/4/1 20:26
 */
@Data
@Accessors(chain = true)
public class ClassLoaderVo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * JVM加载类的数量
     */
    private Integer count;

    /**
     * JVM已加载类数量
     */
    private Long loaded;

    /**
     * JVM未加载类数量
     */
    private Long unLoaded;

    /**
     * 是否启用类加载详细信息的输出
     */
    private Boolean isVerbose;
}