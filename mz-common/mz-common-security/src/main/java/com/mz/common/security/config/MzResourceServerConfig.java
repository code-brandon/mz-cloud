package com.mz.common.security.config;

import com.mz.common.security.resource.IgnoreAllUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * What -- 资源服务器
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.security.config
 * @ClassName: MzResourceServerConfig
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/29 17:29
 */
@Configuration
public class MzResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private IgnoreAllUrlProperties ignoreAllUrlProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests();
        // 指定何人允许访问 URL。
        ignoreAllUrlProperties.getIgnoreUrls().forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated();
    }
}
