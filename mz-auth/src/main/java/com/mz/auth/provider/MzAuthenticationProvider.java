package com.mz.auth.provider;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

/**
 * What -- 自定义额外的身份验证检查
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.auth.provider
 * @ClassName: MzAuthenticationProvider
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/8/28 13:32
 */
@Slf4j
@Component
public class MzAuthenticationProvider extends DaoAuthenticationProvider {

    MzAuthenticationProvider(UserDetailsService userDetailsService){
        super.setUserDetailsService(userDetailsService);
        super.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }



    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 额外的身份验证检查 （获取用户名密码之后）
        JSONObject details = JSONObject.parseObject(JSONObject.toJSONString(authentication.getDetails()));
        String grant_type = details.getString("grant_type");
        log.warn("自定义身份校验：{}",grant_type);
        if (authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();
        if (!super.getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
            this.logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

    }


}
