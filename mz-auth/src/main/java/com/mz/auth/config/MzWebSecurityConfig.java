package com.mz.auth.config;

import com.mz.auth.handler.MzAuthenticationFailureHandler;
import com.mz.auth.handler.MzLogoutSuccessHandler;
import com.mz.auth.provider.MzAuthenticationProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * What -- Security 配置
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.auth.config
 * @ClassName: MzWebSecurityConfig
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/29 17:01
 */
@Configuration
@EnableWebSecurity
public class MzWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MzAuthenticationFailureHandler mzAuthenticationFailureHandler;

    @Autowired
    private MzLogoutSuccessHandler mzLogoutSuccessHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("mzUserDetailsServiceImpl")
    @Autowired
    private UserDetailsService mzUserDetailsServiceImpl;

    @Autowired
    private MzAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //对每个 URL 进行身份验证，并将授予用户“admin”和“user”的访问权限。
                .authorizeRequests()
                // 指定放行资源
                .antMatchers("/oauth/**", "/login/**","/rsa/**")
                .permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // 其他URL都需要认证
                .anyRequest()
                .authenticated()
                .and()
                // 表单认证放行
                .formLogin()
                .loginPage("/oauth/login")
                .loginProcessingUrl("/login")
                .failureForwardUrl("/oauth/login")
                .failureHandler(mzAuthenticationFailureHandler)
                .permitAll()
                .and()
                // 默认为 /logout
                .logout()
                .logoutSuccessHandler(mzLogoutSuccessHandler)
                // 无效会话
                .invalidateHttpSession(true)
                // 清除身份验证
                .clearAuthentication(true)
                .permitAll();
        // http.oauth2ResourceServer().opaqueToken();
    }


    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    /**
     * 不拦截静态资源
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**");
    }

    /**
     * 身份验证管理器生成器
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 根据传入的自定义AuthenticationProvider添加身份验证。由于AuthenticationProvider实现未知，
         * 因此必须在外部完成所有自定义，并立即返回AuthenticationManagerBuilder。
         * {@link ProviderManager#authenticate(Authentication)} 此处进行选择身份验证
         */
        auth.authenticationProvider(authenticationProvider);
        auth.userDetailsService(mzUserDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }
}
