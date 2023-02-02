package com.mz.auth.provider;

import com.mz.common.core.exception.MzException;
import com.mz.common.security.service.MzUserDetailsService;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
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
    
    private final MzUserDetailsService userDetailsService;

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
        String username = details.getUsername();
        if(!details.isEnabled()){
            throw new MzException("对不起，您的账号：" + username + " 已停用");
        }
        parameters.remove("password");
        Authentication userAuth = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        }
        catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        }
        catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }
        // 反射去掉 携带的密码
        try {
            Field credentials = userAuth.getClass().getDeclaredField("credentials");
            credentials.setAccessible(true);
            credentials.set(userAuth, "");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new MzException("字段处理异常",e);
        }
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
    
    @Override
    protected UserDetails getUserDetails(Map<String, String> parameters) {
        String password = parameters.get("password");
        UserDetails userDetails = userDetailsService.loadUserByUsername(parameters);
        // 进行密码校验
        super.checkPassword(password, userDetails.getPassword());
        return userDetails;
    }
}