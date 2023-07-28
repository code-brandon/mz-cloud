package com.mz.system.provider;

import com.mz.common.mybatis.annotation.EnableMzMybatisCustomizeConfig;
import com.mz.common.redis.annotation.EnableMzRedisAutoConfigure;
import com.mz.common.security.annotation.EnableMzResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * What --
 * <br>
 * Describe -- @EnableResourceServer ---> 开启资源服务器
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzSystemProviderApplication
 * @CreateTime 2022/5/31 15:13
 */
@EnableOAuth2Sso
@EnableMzResourceServer
@SpringBootApplication
@EnableDiscoveryClient
@EnableMzRedisAutoConfigure
@EnableMzMybatisCustomizeConfig
@EnableFeignClients(basePackages = {"com.mz.system.api"})
public class MzSystemProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MzSystemProviderApplication.class, args);
    }
}
