package com.mz.auth;

import com.mz.common.redis.annotation.EnableMzRedisAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMzRedisAutoConfigure
@EnableFeignClients(basePackages = {"com.mz.system.api"})
public class MzAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MzAuthApplication.class, args);
    }

}
