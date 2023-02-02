package com.mz.common.security.config;

import com.mz.common.constant.Constant;
import com.mz.common.security.serializer.MzJackson2JsonSerializationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * What -- token 存放配置
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
        // 将存放在redis中的值序列化为JSON TODO 测试发现使用后会出现 User 无法转为 MzSysUserSecurity
        // TODO 解决User 无法转为 MzSysUserSecurity：需要将 MzUserDetailsSecurity 实现 UserDetails, Serializable 接口，而非继承User
        // {@like <a href="https://github.com/moutainhigh/aiadver-parent/tree/ab7d3db5d01aaad215810601946931c8ed9b9183/aiadver-project/aiadver-boot-starter-parent/aiadver-boot-starter-oauth2/src/main/java/com/aiadver/boot/oauth2/token/redis">文章地址</a>}
        tokenStore.setSerializationStrategy(new MzJackson2JsonSerializationStrategy());
        /**
         * {@link <a href = "https://www.csdn.net/tags/MtjaEg3sMTAzNzktYmxvZwO0O0OO0O0O.html"></a>}
         * {@link <a href="https://blog.csdn.net/lki_suidongdong/article/details/105945477/"></a>}
         * TODO 如果反序列化时报错，那请参照错误信息，进行修改。
         */
        // tokenStore.setSerializationStrategy(new MzFastjsonRedisSerializationStrategy());
        // tokenStore.setAuthenticationKeyGenerator();
        return tokenStore;
    }
}
