package com.mz.common.monitor.service.impl;

import com.mz.common.monitor.service.MonitorService;
import com.mz.common.monitor.vo.*;

import java.lang.management.*;
import java.util.*;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.monitor.service.impl
 * @ClassName: MonitorServiceImpl
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/4/1 20:39
 */
public class MonitorServiceImpl implements MonitorService {
    /**
     * 获取 类加载数据
     *
     * @return ClassLoaderVo
     */
    @Override
    public ClassLoaderVo getClassLoader() {
        ClassLoaderVo classLoaderVo = new ClassLoaderVo();
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        classLoaderVo.setLoaded(classLoadingMXBean.getTotalLoadedClassCount());
        classLoaderVo.setCount(classLoadingMXBean.getLoadedClassCount());
        classLoaderVo.setUnLoaded(classLoadingMXBean.getUnloadedClassCount());
        classLoaderVo.setIsVerbose(classLoadingMXBean.isVerbose());
        return classLoaderVo;
    }

    /**
     * 获取 堆内存信息
     *
     * @return MemoryVo
     */
    @Override
    public MemoryVo getMemory() {
        MemoryVo memoryVo = new MemoryVo();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        memoryVo.setCommitted(heapMemoryUsage.getCommitted());
        memoryVo.setInit(heapMemoryUsage.getInit());
        memoryVo.setMax(heapMemoryUsage.getMax());
        memoryVo.setUsed(heapMemoryUsage.getUsed());
        memoryVo.setNonCommitted(nonHeapMemoryUsage.getCommitted());
        memoryVo.setNonInit(nonHeapMemoryUsage.getInit());
        memoryVo.setNonMax(nonHeapMemoryUsage.getMax());
        memoryVo.setNonUsed(nonHeapMemoryUsage.getUsed());
        return memoryVo;
    }

    /**
     * 获取 JVM垃圾回收信息
     *
     * @return List<GarbageCollectorVo>
     */
    @Override
    public List<GarbageCollectorVo> getGarbageCollectors() {
        List<GarbageCollectorVo> garbageCollectorVos = new ArrayList<>(2);
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        garbageCollectorMXBeans.forEach(bean -> {
            GarbageCollectorVo garbageCollectorVo = new GarbageCollectorVo();
            garbageCollectorVo.setCount(bean.getCollectionCount());
            garbageCollectorVo.setTime(bean.getCollectionTime());
            garbageCollectorVos.add(garbageCollectorVo);
        });
        return garbageCollectorVos;
    }

    /**
     * 获取 内存池信息
     * @return List<MemoryPoolVo>
     */
    @Override
    public List<MemoryPoolVo> getMemoryPools() {
        List<MemoryPoolVo> memoryPools = new ArrayList<>(2);
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        memoryPoolMXBeans.forEach(bean -> {
            MemoryPoolVo poolVo = new MemoryPoolVo();
            poolVo.setName(bean.getName());
            poolVo.setManageNames(Arrays.toString(bean.getMemoryManagerNames()));
            poolVo.setUsed(bean.getUsage().getUsed());
            poolVo.setMax(bean.getUsage().getMax());
            poolVo.setCommitted(bean.getUsage().getCommitted());
            memoryPools.add(poolVo);
        });
        return memoryPools;
    }

    /**
     * 获取 操作系统信息
     * @return SystemVo
     */

    @Override
    public SystemVo system() {
        SystemVo system = new SystemVo();
        OperatingSystemMXBean mxBean = ManagementFactory.getOperatingSystemMXBean();
        system.setName(mxBean.getName());
        system.setProcessCount(mxBean.getAvailableProcessors());
        system.setOsArchName(mxBean.getArch());
        system.setLoadAverage(mxBean.getSystemLoadAverage());
        system.setVersion(mxBean.getVersion());
        return system;
    }


    /**
     * 获取 JVM运行时信息
     * @return RuntimeVo
     */
    @Override
    public RuntimeVo getRuntime() {
        RuntimeVo runtimeVo = new RuntimeVo();
        RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
        runtimeVo.setHost(mxBean.getName().concat(" ").concat(mxBean.getSystemProperties().get("os.name")).concat(" ").concat(mxBean.getSystemProperties().get("os.version")));
        runtimeVo.setJvm(mxBean.getVmName().concat(" ").concat(mxBean.getVmVendor()));
        runtimeVo.setArgs(mxBean.getInputArguments());
        runtimeVo.setVersion(mxBean.getSystemProperties().get("java.runtime.version").concat(" ").concat(mxBean.getSystemProperties().get("java.runtime.name")));
        runtimeVo.setHome(mxBean.getSystemProperties().get("java.home"));
        runtimeVo.setStartTime(mxBean.getStartTime());

        List<Map<String,Object>> properties = new ArrayList<>(5);
        mxBean.getSystemProperties().forEach((key, value) -> {
            HashMap<String, Object> format = new HashMap<>(1);
            format.put(key, value);
            properties.add(format);
        });
        runtimeVo.setProperties(properties);
        return runtimeVo;
    }

    /**
     * 获取 线程信息
     * @return ThreadVo
     */
    @Override
    public ThreadVo getThread() {
        ThreadVo thread = new ThreadVo();
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        thread.setCurrentTime(mxBean.getCurrentThreadUserTime());
        thread.setDaemonCount(mxBean.getDaemonThreadCount());
        thread.setCount(mxBean.getThreadCount());
        return thread;
    }

    /**
     * 获取 编译信息
     * @return CompilationVo
     */

    public CompilationVo getCompilation() {
        CompilationVo compilation = new CompilationVo();
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        compilation.setName(compilationMXBean.getName());
        compilation.setTotalTime(compilationMXBean.getTotalCompilationTime());
        compilation.setIsSupport(compilationMXBean.isCompilationTimeMonitoringSupported());
        return compilation;
    }
}
