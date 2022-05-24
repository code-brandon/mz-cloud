package com.mz.gateway.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;

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
@ComponentScan(basePackages = {"springfox.documentation.swagger.web"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {InMemorySwaggerResourcesProvider.class})})
@SpringBootApplication
public class MzGatewayProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MzGatewayProviderApplication.class, args);
    }

}
