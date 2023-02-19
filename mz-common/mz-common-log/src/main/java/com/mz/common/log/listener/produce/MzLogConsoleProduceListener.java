package com.mz.common.log.listener.produce;

import cn.hutool.json.JSONStrFormatter;
import cn.hutool.json.JSONUtil;
import com.mz.common.log.entity.SysOperLog;
import com.mz.common.log.listener.MzLogProduceListener;
import com.mz.common.utils.IPSearcherUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * What -- 控制台打印日志
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.log.listener
 * @ClassName: MzLogRedisConsumeListener
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/14 22:37
 */
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnMissingBean(MzLogConsoleProduceListener.class)
@Slf4j
public class MzLogConsoleProduceListener implements MzLogProduceListener {

    @Async
    public void produceLog(SysOperLog sysOperLog) {
        sysOperLog.setOperLocation(IPSearcherUtils.searcher(sysOperLog.getOperIp()));
        log.info("MzLog日志记录：\n{}", JSONStrFormatter.format(JSONUtil.toJsonStr(sysOperLog)));
    }
}
