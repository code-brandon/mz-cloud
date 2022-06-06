package com.mz.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * What -- Mz 密码编码器配置
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.security.config
 * @ClassName: MzPasswordEncoderConfig
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/6 19:36
 */
@Configuration
public class MzPasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
