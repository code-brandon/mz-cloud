package com.mz.common.feign.config;

import com.mz.common.constant.Constant;
import com.mz.common.feign.exception.MzFeignExceptionHandler;
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
                // 同步请求头  给新请求同步了老请求的cookie
                template.header("Cookie", request.getHeader("Cookie"));
                String env = request.getHeader(Constant.GATEWAY_ENV);
                if ("".equals(env)) {
                    // 灰度环境标识
                    template.header(Constant.GATEWAY_ENV, env);
                }
            }
        };
    }

    /**
     * 使用Feign 替换掉WebMvc , TODO 注意Feign的契约 否则抛出以下类似异常
     * - Class MzSysUcerApi has annotations [FeignClient] that are not used by contract Default
     * - Method loadUserByUserName has an annotation PostMapping that is not used by contract Default
     * @return
     */
    // @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    @Bean
    public MzFeignExceptionHandler mzFeignExceptionHandler(){
        return new MzFeignExceptionHandler();
    }
}