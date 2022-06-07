package com.mz.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableDiscoveryClient
@EnableAuthorizationServer
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.mz.system.api"})
public class MzAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MzAuthApplication.class, args);
    }

}
