package com.mz.common.security.config;

import com.mz.common.core.constants.Constant;
import com.mz.common.security.serializer.MzJackson2JsonSerializationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.security.config
 * @ClassName: MzTokenStoreConfig
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/31 15:09
 */
@Configuration
public class MzTokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore tokenStore() {
        // 将token 放在redis中
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(Constant.OAUTH_KEY);
        // 将存放在redis中的值序列化为JSON
        // {@link https://github.com/moutainhigh/aiadver-parent/tree/ab7d3db5d01aaad215810601946931c8ed9b9183/aiadver-project/aiadver-boot-starter-parent/aiadver-boot-starter-oauth2/src/main/java/com/aiadver/boot/oauth2/token/redis}
        tokenStore.setSerializationStrategy(new MzJackson2JsonSerializationStrategy());
        /*tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                return super.extractKey(authentication) + StrUtil.COLON + UUIDUtils.;
            }
        });*/
        return tokenStore;
    }
}
