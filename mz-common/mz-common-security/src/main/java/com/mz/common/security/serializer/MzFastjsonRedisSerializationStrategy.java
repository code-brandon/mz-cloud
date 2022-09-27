package com.mz.common.security.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.Preconditions;
import com.mz.common.security.entity.MzUserDetailsSecurity;
import com.mz.common.security.serializer.authen.OAuth2AuthenticationFastjsonDeserializer;
import com.mz.common.security.serializer.refresh.MzFastjsonOauth2RefreshTokenSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.nio.charset.Charset;

/**
 * What -- fastjson redis存储json格式序列化反序列化工具类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzFastjsonRedisSerializationStrategy
 * @CreateTime 2022/8/27 23:43
 */
public class MzFastjsonRedisSerializationStrategy implements RedisTokenStoreSerializationStrategy {

    /**
     * 解析适配器
     */
    private static ParserConfig config = new ParserConfig();
 
    static {
        init();
    }
 
    protected static void init() {
        //自定义oauth2序列化：DefaultOAuth2RefreshToken 没有setValue方法，会导致JSON序列化为null
        config.setAutoTypeSupport(true);//开启AutoType
        //自定义DefaultOauth2RefreshTokenSerializer反序列化
        config.putDeserializer(DefaultOAuth2RefreshToken.class, new MzFastjsonOauth2RefreshTokenSerializer());
        //自定义OAuth2Authentication反序列化
        config.putDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationFastjsonDeserializer());
        //TODO 添加autotype白名单
        config.addAccept("org.springframework.security.oauth2.provider.");
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.OAuth2Authentication",
                OAuth2Authentication.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.client.BaseClientDetails",
                BaseClientDetails.class);
 
        config.addAccept("org.springframework.security.oauth2.common.");
        TypeUtils.addMapping("org.springframework.security.oauth2.common.DefaultOAuth2AccessToken", DefaultOAuth2AccessToken.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken", DefaultExpiringOAuth2RefreshToken.class);
 
        config.addAccept("com.mz.common.security.entity");
        TypeUtils.addMapping("com.mz.common.security.entity.MzUserDetailsSecurity", MzUserDetailsSecurity.class);
 
        config.addAccept("org.springframework.security.web.authentication.preauth.");
        TypeUtils.addMapping("org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken", PreAuthenticatedAuthenticationToken.class);
    }
 
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> aClass) {
        Preconditions.checkArgument(aClass != null,
                "clazz can't be null");
        if (bytes == null || bytes.length == 0) {
            return null;
        }
 
        try {
            return JSON.parseObject(new String(bytes, IOUtils.UTF8), aClass, config);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }
 
    @Override
    public String deserializeString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(bytes, IOUtils.UTF8);
    }
 
    @Override
    public byte[] serialize(Object o) {
        if (o == null) {
            return new byte[0];
        }
 
        try {
            return JSON.toJSONBytes(o, SerializerFeature.WriteClassName,
                    SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }
 
    @Override
    public byte[] serialize(String data) {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }
 
        return data.getBytes(Charset.forName("utf-8"));
    }
}