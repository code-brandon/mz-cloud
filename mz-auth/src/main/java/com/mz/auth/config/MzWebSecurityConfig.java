package com.mz.auth.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //对每个 URL 进行身份验证，并将授予用户“admin”和“user”的访问权限。
                .authorizeRequests()
                // 指定放行资源
                .antMatchers("/oauth/**", "/login/**", "/logout/**")
                .permitAll()
                // 其他URL都需要认证
                .anyRequest()
                .authenticated()
                .and()
                // 表单认证放行
                .formLogin()
                .permitAll();
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
}
