package com.mz.common.log.listener;

import com.mz.common.log.entity.SysOperLog;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.log.listener
 * @ClassName: MzLogConsumeListener
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/18 0:26
 */
public interface MzLogProduceListener {

    void produceLog(SysOperLog sysOperLog);
}
