package com.mz.common.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * What -- 线程信息
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: ThreadVo
 * @CreateTime 2023/4/1 23:29
 */
@Data
@Accessors(chain = true)
public class ThreadVo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 当前线程执行时间（纳秒）
     */
    private Long currentTime;

    /**
     * 当前守护线程数量
     */
    private Integer daemonCount;

    /**
     * 当前线程总数量（包括守护线程和非守护线程）
     */
    private Integer count;
}