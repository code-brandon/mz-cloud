package com.mz.auth.config;

import com.mz.auth.handler.MzWebResponseExceptionTranslator;
import com.mz.auth.provider.MzJwtTokenEnhancer;
import com.mz.auth.provider.WxUserPasswdTokenGranter;
import com.mz.common.security.service.MzUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
@Slf4j
public class MzAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${mz.token.is-jwt-token-enhance:false}")
    private boolean isJwtTokenEnhance;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * token 存放配置
     */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    @Qualifier("mzJwtAccessTokenConverter")
    private JwtAccessTokenConverter mzJwtAccessTokenConverter;

    /**
     * 对JWT内容增强器
     */
    @Autowired
    private MzJwtTokenEnhancer mzJwtTokenEnhancer;

    /**
     * 用户详细信息服务
     */
    @Qualifier("mzUserDetailsServiceImpl")
    @Autowired
    private MzUserDetailsService mzUserDetailsServiceImpl;


    /**
     * 配置授权服务器的安全性
     *
     * @param oauthServer a fluent configurer for security features
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 允许客户端的表单身份验证
        AuthorizationServerSecurityConfigurer securityConfigurer = oauthServer.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("isAuthenticated()");

    }

    /**
     * 客户端详细信息配置
     *
     * @param clients the client details configurer
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端ID
                .withClient("mz_cloud")
                // 默认使用加密校验
                .secret(passwordEncoder.encode("1911298402"))
                // 令牌有效期 5小时
                .accessTokenValiditySeconds(60 * 60 * 5)
                // 刷新令牌有效时间 3天
                .refreshTokenValiditySeconds(60 * 60 * 24 * 3)
                // 授权成功进行跳转
                .redirectUris("https://www.baidu.com")
                // 配置申请权限范围
                .scopes("all")
                // 配置授权类型
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token", "custom_wxmini");
    }

    /**
     * 授权服务器端点配置器（密码模式）
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        log.warn("是否 Jwt 令牌增强：{}", isJwtTokenEnhance);
        if (isJwtTokenEnhance) {
            delegates.add(mzJwtTokenEnhancer);
            delegates.add(mzJwtAccessTokenConverter);
        }
        //配置JWT的内容增强器
        enhancerChain.setTokenEnhancers(delegates);

        // 令牌增强器
        endpoints.tokenEnhancer(enhancerChain);

        // 用于密码授予
        endpoints.authenticationManager(authenticationManager)
                // 用户详情服务（实现查询数据库进行登录）
                .userDetailsService(mzUserDetailsServiceImpl)
                // token 存放规则
                .tokenStore(tokenStore)
                // 访问令牌转换器
                // .accessTokenConverter(mzJwtAccessTokenConverter)
                // 允许的令牌端点请求方法
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 认证失败处理
                .exceptionTranslator(new MzWebResponseExceptionTranslator());
        // 自定义 令牌授予者 (自定义授权类型)
        endpoints.tokenGranter((grantType, tokenRequest) -> {
            CompositeTokenGranter granter = new CompositeTokenGranter(new ArrayList<>(Arrays.asList(
                    new AuthorizationCodeTokenGranter(
                            endpoints.getTokenServices(),
                            endpoints.getAuthorizationCodeServices(),
                            endpoints.getClientDetailsService(),
                            endpoints.getOAuth2RequestFactory()),
                    new RefreshTokenGranter(
                            endpoints.getTokenServices(),
                            endpoints.getClientDetailsService(),
                            endpoints.getOAuth2RequestFactory()),
                    new ClientCredentialsTokenGranter(
                            endpoints.getTokenServices(),
                            endpoints.getClientDetailsService(),
                            endpoints.getOAuth2RequestFactory()),
                    new ResourceOwnerPasswordTokenGranter(
                            authenticationManager,
                            endpoints.getTokenServices(),
                            endpoints.getClientDetailsService(),
                            endpoints.getOAuth2RequestFactory()),
                    new WxUserPasswdTokenGranter(
                            authenticationManager,
                            endpoints.getTokenServices(),
                            endpoints.getClientDetailsService(),
                            endpoints.getOAuth2RequestFactory(),
                            mzUserDetailsServiceImpl,
                            passwordEncoder)
            )
            ));
            return granter.grant(grantType, tokenRequest);
        });
    }
}
