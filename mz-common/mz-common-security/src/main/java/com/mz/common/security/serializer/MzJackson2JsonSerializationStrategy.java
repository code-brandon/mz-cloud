package com.mz.common.security.serializer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.common.security.serializer.access.Oauth2AccessTokenMixIn;
import com.mz.common.security.serializer.authen.Oauth2AuthenticationMixIn;
import com.mz.common.security.serializer.request.Oauth2RequestMixIn;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;
import org.springframework.security.web.jackson2.WebJackson2Module;

/**
 * What -- 自定义 Jackson2 Json 序列化策略
 * <br>
 * Describe -- 用于对OAuth 存入Redis值的序列化
 * <br>
 * TODO 测试发现使用后会出现 User 无法转为 MzSysUserSecurity
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzJackson2JsonSerializationStrategy
 * @CreateTime 2022/5/31 10:50
 */
public class MzJackson2JsonSerializationStrategy extends StandardStringSerializationStrategy {

    private static GenericJackson2JsonRedisSerializer jsonRedisSerializer;

    public MzJackson2JsonSerializationStrategy() {
        ObjectMapper objectMapper = getObjectMapper();
        jsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        objectMapper.registerModule(new CoreJackson2Module());
        objectMapper.registerModule(new WebJackson2Module());
        objectMapper.addMixIn(OAuth2AccessToken.class, Oauth2AccessTokenMixIn.class);
        objectMapper.addMixIn(OAuth2Authentication.class, Oauth2AuthenticationMixIn.class);
        objectMapper.addMixIn(OAuth2Request.class, Oauth2RequestMixIn.class);
        return objectMapper;
    }

    @Override
    protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
        return jsonRedisSerializer.deserialize(bytes, clazz);
    }

    @Override
    protected byte[] serializeInternal(Object object) {
        return jsonRedisSerializer.serialize(object);
    }
}