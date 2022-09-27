package com.mz.auth.provider;

import com.mz.common.core.exception.MzException;
import com.mz.common.security.service.MzUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * What -- Wx 用户密码令牌授予者
 * <br>
 * Describe -- 自定义令牌授予，扩展登录 （TODO 自己修改用来完成微信登录）
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: WxUserPasswdTokenGranter
 * @CreateTime 2022/7/27 9:28
 */
public class WxUserPasswdTokenGranter extends MzAbstractTokenGranter {
    
    
    private static final String MERCHANT = "custom_wxmini";
    
    private final AuthenticationManager authenticationManager;
    
    private MzUserDetailsService userDetailsService;

    public WxUserPasswdTokenGranter(AuthenticationManager authenticationManager,
                                    AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                    OAuth2RequestFactory requestFactory, MzUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, userDetailsService, MERCHANT,passwordEncoder);
    }
    
    public WxUserPasswdTokenGranter(AuthenticationManager authenticationManager,
                                    AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                    OAuth2RequestFactory requestFactory, MzUserDetailsService userDetailsService, String grantType, PasswordEncoder passwordEncoder) {
        super(tokenServices, clientDetailsService, requestFactory, grantType,passwordEncoder);
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        UserDetails details = getUserDetails(parameters);
        if(!details.isEnabled()){
            throw new MzException("");
        }
        parameters.remove("password");
        Authentication userAuth = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());

        // 反射去掉 携带的密码
        try {
            Field credentials = userAuth.getClass().getDeclaredField("credentials");
            credentials.setAccessible(true);
            credentials.set(userAuth, "");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
    
    @Override
    protected UserDetails getUserDetails(Map<String, String> parameters) {
        String username = parameters.get("username");
        String password = parameters.get("password");
        UserDetails userDetails = userDetailsService.loadUserByUsername(parameters);
        // 进行密码校验
        super.checkPassword(password, userDetails.getPassword());
        return userDetails;
    }
}