package com.mz.common.log.listener.produce;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONStrFormatter;
import cn.hutool.json.JSONUtil;
import com.mz.common.core.exception.MzRemoteException;
import com.mz.common.log.entity.SysOperLog;
import com.mz.common.log.listener.MzLogProduceListener;
import com.mz.common.utils.IPSearcherUtils;
import com.mz.system.api.MzSysOperLogApi;
import com.mz.system.model.dto.SysOperLogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

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
@ConditionalOnMissingBean(MzLogRemoteApiProduceListener.class)
@Slf4j
public class MzLogRemoteApiProduceListener implements MzLogProduceListener {

    @Resource
    private MzSysOperLogApi mzSysOperLogApi;

    @Async
    public void produceLog(SysOperLog sysOperLog) {

        try {
            SysOperLogDto sysOperLogDto = BeanUtil.copyProperties(sysOperLog, SysOperLogDto.class);
            sysOperLogDto.setOperLocation(IPSearcherUtils.searcher(sysOperLog.getOperIp()));
            mzSysOperLogApi.save(sysOperLogDto);
        } catch (Exception e) {
            log.warn("MzLog远程日志记录：\n{}", JSONStrFormatter.format(JSONUtil.toJsonStr(sysOperLog)));
            throw new MzRemoteException(e.getLocalizedMessage());
        }
    }
}
