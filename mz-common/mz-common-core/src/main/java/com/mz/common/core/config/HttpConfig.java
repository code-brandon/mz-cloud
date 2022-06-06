package com.mz.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.core.config
 * @ClassName: HttpConfig
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/6 9:10
 */
public class HttpConfig {
    /**
     * 两分钟超时时间
     */
    private int outtime = 2 * 60 * 1000;
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(outtime);
        requestFactory.setReadTimeout(outtime);
        return new RestTemplate();
    }
}
