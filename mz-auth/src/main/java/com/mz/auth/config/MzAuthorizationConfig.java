package com.mz.auth.config;

import com.mz.auth.handler.MzWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;


/**
 * What -- 配置 OAUth2 授权服务器
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.auth.config
 * @ClassName: MzAuthorizationConfig
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/29 17:21
 */
@Configuration
public class MzAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Qualifier("mzUserDetailsServiceImpl")
    @Autowired
    private UserDetailsService MzUserDetailsServiceImpl;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 允许客户端的表单身份验证
        oauthServer.allowFormAuthenticationForClients().checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端ID
                .withClient("mz_cloud")
                .secret(passwordEncoder.encode("1911298402"))
                // 令牌有效期
                .accessTokenValiditySeconds(60 * 60 * 5)
                // 授权成功进行跳转
                .redirectUris("https://www.baidu.com")
                // 配置申请权限范围
                .scopes("all")
                // 配置授权类型
                .authorizedGrantTypes("authorization_code","password","client_credentials", "implicit", "refresh_token");
    }

    /**
     * 授权服务器端点配置器（密码模式）
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(MzUserDetailsServiceImpl)
                .tokenStore(tokenStore)
                // 允许的令牌端点请求方法
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 认证失败处理
                .exceptionTranslator(new MzWebResponseExceptionTranslator());
    }
}
