package com.mz.gateway;

import com.mz.common.redis.annotation.EnableMzRedisAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * What -- MZ微服务网关启动类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzCommonGatewayProviderApplication
 * @CreateTime 2022/5/20 21:16
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableMzRedisAutoConfigure
public class MzGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MzGatewayApplication.class, args);
    }

}
