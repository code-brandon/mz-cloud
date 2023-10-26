package com.mz.common.security.config;

import com.mz.common.constant.MzConstant;
import com.mz.common.security.handler.MzAccessDeniedHandler;
import com.mz.common.security.handler.MzAuthenticationEntryPointHandler;
import com.mz.common.security.opaquetoken.MzUserAuthenticationConverter;
import com.mz.common.security.resource.IgnoreAllUrlProperties;
import com.mz.common.utils.SpringContextHolderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

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
@Order(0)
@Configuration
// 开启SpringSecurity注解支持
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MzResourceServerConfig extends ResourceServerConfigurerAdapter {


    // @Autowired
    // private TokenStore tokenStore;

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
        registry.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
        // 指定何人允许访问 URL。
        ignoreAllUrlProperties.getIgnoreUrls().forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated();
        // 一般formLogin和httpBasic选一个就可，如果想要Basic认证，就把formLogin()删掉，就可以保证一定弹窗了
        registry.and().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                // .tokenStore(tokenStore)//令牌策略
                .authenticationEntryPoint(mzAuthenticationEntryPointHandler)//token异常类重写
                .accessDeniedHandler(mzAccessDeniedHandler);//权限不足异常类重写
        resources.tokenServices(remoteTokenServices());
    }

    @Bean
    HttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }

    /**
     * 远程令牌服务
     */
    @Autowired
    private RemoteTokenServices remoteTokenServices;

    /**
     * 远程请求 获取登录用户信息
     * @return
     */
    public ResourceServerTokenServices remoteTokenServices() {
        RestTemplate restTemplate = new RestTemplate();
        // fix:通过拦截器携带自定义 Header 信息
        restTemplate.getInterceptors().add((httpRequest, bytes, execution) -> {
            HttpServletRequest request = SpringContextHolderUtils.getRequest();
            httpRequest.getHeaders().set(MzConstant.GATEWAY_ENV, request.getHeader(MzConstant.GATEWAY_ENV));
            httpRequest.getHeaders().set("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
            httpRequest.getHeaders().set("cookie", request.getHeader("cookie"));
            return execution.execute(httpRequest, bytes);
        });
        remoteTokenServices.setRestTemplate(restTemplate);
        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(new MzUserAuthenticationConverter());
        remoteTokenServices.setAccessTokenConverter(tokenConverter);
        return remoteTokenServices;
    }


}
