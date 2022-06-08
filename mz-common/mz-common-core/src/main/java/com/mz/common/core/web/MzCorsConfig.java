package com.mz.common.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;
import java.util.List;

/**
 * What -- 配置跨域
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzCorsConfig
 * @CreateTime 2022/6/8 18:12
 */
@Configuration
public class MzCorsConfig {
 
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> allowedMethods = Arrays.asList("POST", "GET", "DELETE", "PUT", "OPTIONS");
        List<String> allowed = Arrays.asList("*");
        config.setAllowedHeaders(allowed);
        config.setAllowedMethods(allowedMethods);
        config.setAllowedOrigins(allowed);
        config.setExposedHeaders(allowed);
        config.setMaxAge(36000L);
        // 是否允许携带cooker跨域
        config.setAllowCredentials(true);

        // org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
 
        return new CorsWebFilter(source);
    }
}