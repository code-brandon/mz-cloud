package com.mz.system.provider;

import com.mz.common.mybatis.annotation.EnableMzMybatisCustomizeConfig;
import com.mz.common.redis.annotation.EnableMzRedisAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

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
@EnableResourceServer
@SpringBootApplication
@EnableDiscoveryClient
@EnableMzRedisAutoConfigure
@EnableMzMybatisCustomizeConfig
public class MzSystemProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MzSystemProviderApplication.class, args);
    }
}
