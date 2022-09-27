package com.mz.auth.provider;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;
import java.util.Objects;

/**
 * What -- Mz 抽象令牌授予者
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.auth.provider
 * @ClassName: MzAbstractTokenGranter
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/8/28 15:38
 */
public abstract class MzAbstractTokenGranter extends AbstractTokenGranter {

    private final PasswordEncoder passwordEncoder;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    protected MzAbstractTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType, PasswordEncoder passwordEncoder) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        // 获取传入授权端点或令牌端点的参数映射
        Map<String, String> parameters = tokenRequest.getRequestParameters();
        UserDetails details = getUserDetails(parameters);
        if (Objects.isNull(details)) {
            throw new InvalidGrantException("账户未找到");
        }
        Authentication userAuth = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

    /**
     * 自定义获取用户信息
     */
    protected abstract UserDetails getUserDetails(Map<String, String> parameters);

    protected void checkPassword(CharSequence rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }
}
