package com.mz.auth.provider;

import com.mz.common.security.entity.MzUserDetailsSecurity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * What -- 对JWT内容增强器
 * <br>
 * Describe -- 可以对 JWT DATA内容进行自定义
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzJwtTokenEnhancer
 * @CreateTime 2022/8/29 20:11
 */
@Component
public class MzJwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        MzUserDetailsSecurity securityUser = (MzUserDetailsSecurity) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>(5);
        //把用户ID设置到JWT中
        info.put("userId", securityUser.getUserId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}