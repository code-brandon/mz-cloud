package com.mz.common.log.listener.consume;

import cn.hutool.json.JSONStrFormatter;
import com.mz.common.log.listener.MzLogConsumeListener;
import com.mz.common.log.listener.produce.MzLogRedisProduceListener;
import com.mz.common.redis.utils.MzRedisUtil;
import com.mz.common.utils.MzUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
@Component
@ConditionalOnMissingBean(MzLogRedisConsumeListener.class)
@ConditionalOnClass({RedisOperations.class,MzLogRedisProduceListener.class})
@Slf4j
public class MzLogRedisConsumeListener implements MzLogConsumeListener {

    @Resource
    private MzRedisUtil mzRedisUtil;

    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);

    @PostConstruct
    public void consumeLog() {
        StringRedisTemplate redisTemplate = mzRedisUtil.getRedisTemplate();

        for (int i = 0; i < 5; i++) {
            fixedThreadPool.execute(()->{
                while (true) {
                    try {
                        if (Thread.currentThread().isInterrupted()){
                            break;
                        }
                        String pop = redisTemplate.opsForList().rightPop(MzLogRedisProduceListener.CHANNEL, 5L, TimeUnit.SECONDS);
                        if (MzUtils.notEmpty(pop)) {
                            log.warn("\n{}", JSONStrFormatter.format(pop));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public MzRedisUtil getMzRedisUtil() {
        return mzRedisUtil;
    }

    public ExecutorService getFixedThreadPool() {
        return fixedThreadPool;
    }

    @PreDestroy
    private void destroyed() {
        fixedThreadPool.shutdown();
    }
}
