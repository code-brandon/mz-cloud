package com.mz.common.security.config;

import com.mz.common.security.handler.MzAccessDeniedHandler;
import com.mz.common.security.handler.MzAuthenticationEntryPointHandler;
import com.mz.common.security.resource.IgnoreAllUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

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
    private TokenStore tokenStore;

    @Autowired
    private IgnoreAllUrlProperties ignoreAllUrlProperties;

    @Autowired
    private MzAuthenticationEntryPointHandler mzAuthenticationEntryPointHandler;

    @Autowired
    private MzAccessDeniedHandler mzAccessDeniedHandler;

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

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore)//令牌策略
                .authenticationEntryPoint(mzAuthenticationEntryPointHandler)//token异常类重写
                .accessDeniedHandler(mzAccessDeniedHandler);//权限不足异常类重写
    }
}
