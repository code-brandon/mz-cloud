package com.mz.common.redis.config;

import com.mz.common.redis.utils.MzRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * What -- Cache配置类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName MzCacheConfig
 * @CreateTime 2022/5/22 14:59
 */
@Configuration
//启用配置属性 将CacheProperties.class加入Spring容器中
@EnableConfigurationProperties(CacheProperties.class)
@EnableCaching // 开启缓存功能
@Slf4j
public class MzCacheConfig {

    @Bean
    RedisCacheConfiguration redisCacheConfiguration(/*因为开启了配置属性功能并导入了class所以这样是可以自动导入的*/CacheProperties cacheProperties) {
        // 在默认配置的基础上进行修改  可以链式调用修改配置
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        // 序列化Key 使用Java字符串 Redis 序列化程序
        configuration = configuration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        // 序列化Value 使用Jackson2 Redis 序列化程序 （Spring的官方推荐的）
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        // 读取Redis配置文件类,不设置，可能yml中的设置不生效
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            configuration = configuration.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            configuration = configuration.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            configuration = configuration.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            configuration = configuration.disableKeyPrefix();
        }
        log.info("Cache配置类完成:{}",configuration.getClass().toString());
        return configuration;
    }

    @Bean
    public MzRedisUtil mzRedisUtil() {
        return new MzRedisUtil();
    }
}