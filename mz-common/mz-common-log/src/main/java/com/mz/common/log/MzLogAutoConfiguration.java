package com.mz.common.log;

import com.mz.common.log.aspectj.MzLogAspectj;
import com.mz.common.log.event.MzLogEventListener;
import com.mz.common.log.listener.consume.MzLogRedisConsumeListener;
import com.mz.common.log.listener.produce.MzLogConsoleProduceListener;
import com.mz.common.log.listener.produce.MzLogRedisProduceListener;
import com.mz.common.log.listener.produce.MzLogRemoteApiProduceListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.log
 * @ClassName: MzLogAutoConfiguration
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/18 0:52
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class MzLogAutoConfiguration {

    @Bean
    public MzLogAspectj mzLogAspectj(){
        return new MzLogAspectj();
    }

    @Bean
    public MzLogRedisConsumeListener mzLogRedisConsumeListener() {
        return new MzLogRedisConsumeListener();
    }

    @Bean

    @ConditionalOnExpression("#{'redis'.equals(environment['mz.log.produce'])}")
    public MzLogRedisProduceListener mzLogRedisProduceListener(){
        return new MzLogRedisProduceListener();
    }

    @Bean

    @ConditionalOnExpression("#{'remote'.equals(environment['mz.log.produce'])}")
    public MzLogRemoteApiProduceListener mzLogRemoteApiProduceListenerr(){
        return new MzLogRemoteApiProduceListener();
    }

    @Bean
    @ConditionalOnMissingBean({MzLogRedisProduceListener.class,MzLogRemoteApiProduceListener.class})
    public MzLogConsoleProduceListener mzLogConsoleProduceListener(){
        return new MzLogConsoleProduceListener();
    }

    @Bean
    public MzLogEventListener mzLogEventListener() {
        return new MzLogEventListener();
    }

}
