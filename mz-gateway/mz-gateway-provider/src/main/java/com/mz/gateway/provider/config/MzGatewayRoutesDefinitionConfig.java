package com.mz.gateway.provider.config;

import com.mz.gateway.provider.routes.MzGatewayRoutesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * What -- 网关路由定义配置
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzGatewayRoutesDefinitionConfig
 * @CreateTime 2022/5/20 21:05
 */
@Configuration
@Slf4j
public class MzGatewayRoutesDefinitionConfig {


    @Bean
    RouteDefinitionLocator routeDefinitionLocator(){
        return new MzGatewayRoutesRepository();
    }
        
    @Bean
    @Primary
    RouteDefinitionWriter routeDefinitionWriter(){
        return new MzGatewayRoutesRepository();
    }
}


