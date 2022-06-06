package com.mz.common.feign.config;

import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * What -- Mz Feign 配置
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzFeignConfiguration
 * @CreateTime 2022/6/6 10:05
 */
@Configuration
public class MzFeignConfiguration {
 
    /**
     * 日志级别
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // RequestContextHolder 拿到刚进来的请求
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(requestAttributes)) {
                // 当前请求 老请求
                HttpServletRequest request = requestAttributes.getRequest();
                // 不为空同步请求头
                if (Objects.nonNull(request)) {
                    // 同步请求头  给新请求同步了老请求的cookie
                    template.header("Cookie", request.getHeader("Cookie"));
                }
            }
        };
    }

    /**
     * 使用Feign 替换掉WebMvc
     * @return
     */
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }
}