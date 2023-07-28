package com.mz.common.monitor.service;

import com.mz.common.monitor.vo.*;

import java.util.List;

/**
 * What -- 监控服务
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.monitor.service
 * @ClassName: MonitorService
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/4/1 20:36
 */
public interface MonitorService {

    /**
     * 获取 类加载数据
     * @return ClassLoaderVo
     */
    ClassLoaderVo getClassLoader();

    /**
     * 获取 堆内存信息
     * @return MemoryVo
     */
    MemoryVo getMemory();

    /**
     * 获取 JVM垃圾回收信息
     *
     * @return List<GarbageCollectorVo>
     */
    List<GarbageCollectorVo> getGarbageCollectors();

    /**
     * 获取 内存池信息
     * @return List<MemoryPoolVo>
     */
    List<MemoryPoolVo> getMemoryPools();

    /**
     * 获取 操作系统信息
     * @return SystemVo
     */
    SystemVo system();

    /**
     * 获取 JVM运行时信息
     * @return RuntimeVo
     */
    RuntimeVo getRuntime();

    /**
     * 获取 线程信息
     * @return ThreadVo
     */
    ThreadVo getThread();
}
