package com.mz.common.sentinel;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.mz.common.sentinel.handle.MzSentinelUrlBlockHandler;
import com.mz.common.sentinel.init.MzInitFunc;
import com.mz.common.sentinel.regular.RequestOriginParserDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.sentinel
 * @ClassName: MzSentinelAutoConfiguration
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/3 22:38
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class MzSentinelAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler() {
        return new MzSentinelUrlBlockHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestOriginParser requestOriginParser() {
        return new RequestOriginParserDefinition();
    }

    @Bean
    @ConditionalOnMissingBean
    @Order
    public MzInitFunc mzInitFunc() {
        return new MzInitFunc();
    }
}
