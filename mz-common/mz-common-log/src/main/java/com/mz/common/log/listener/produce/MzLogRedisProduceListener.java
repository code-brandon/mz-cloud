package com.mz.common.log.listener.produce;

import cn.hutool.json.JSONUtil;
import com.mz.common.constant.Constant;
import com.mz.common.log.entity.SysOperLog;
import com.mz.common.log.listener.MzLogProduceListener;
import com.mz.common.redis.exception.MzRedisException;
import com.mz.common.redis.utils.MzRedisUtil;
import com.mz.common.utils.IPSearcherUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * What -- Redis生产日志
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.log.listener
 * @ClassName: MzLogRedisConsumeListener
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/14 22:37
 */
@Slf4j
@Component
@ConditionalOnMissingBean(MzLogRedisProduceListener.class)
@ConditionalOnClass({RedisOperations.class})
public class MzLogRedisProduceListener implements MzLogProduceListener {

    public final static String CHANNEL = "mz_log:channel_log";

    @Resource
    private MzRedisUtil mzRedisUtil;

    public void produceLog(SysOperLog sysOperLog) {

        StringRedisTemplate redisTemplate = mzRedisUtil.getRedisTemplate();
        redisTemplate.execute((RedisCallback<Long>) conn -> {
            try {
                sysOperLog.setOperLocation(IPSearcherUtils.searcher(sysOperLog.getOperIp()));
                String jsonStr = JSONUtil.toJsonStr(sysOperLog);
                Long aLong = conn.listCommands().lLen(CHANNEL.getBytes(Constant.UTF8));
                if (aLong < 10000) {
                    return conn.lPush(CHANNEL.getBytes(Constant.UTF8), jsonStr.getBytes(Constant.UTF8));
                }else {
                    log.warn("日志队列已满：{}",aLong);
                    log.warn("日志队列已满===>：{}",jsonStr );
                }
                return 0L;
            } catch (UnsupportedEncodingException e) {
                throw new MzRedisException(e.getLocalizedMessage());
            }
        });

    }
}
