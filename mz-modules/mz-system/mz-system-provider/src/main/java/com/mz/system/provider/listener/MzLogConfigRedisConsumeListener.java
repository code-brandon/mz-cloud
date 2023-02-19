package com.mz.system.provider.listener;

import cn.hutool.json.JSONStrFormatter;
import com.mz.common.log.listener.consume.MzLogRedisConsumeListener;
import com.mz.common.log.listener.produce.MzLogRedisProduceListener;
import com.mz.common.utils.MzUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * What --
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
@NoArgsConstructor
@Component
public class MzLogConfigRedisConsumeListener extends MzLogRedisConsumeListener {


    /**
     *
     */
    @Override
    public void consumeLog() {
        StringRedisTemplate redisTemplate = getMzRedisUtil().getRedisTemplate();

        getFixedThreadPool().execute(()->{
            while (true) {
                // rPop(MzLogAspectj.CHANNEL.getBytes(StandardCharsets.UTF_8))
                try {
                    String pop = redisTemplate.opsForList().rightPop(MzLogRedisProduceListener.CHANNEL, 5L, TimeUnit.SECONDS);
                    if (MzUtils.notEmpty(pop)) {
                        log.info("重写日志");
                        log.warn("\n{}", JSONStrFormatter.format(pop));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

