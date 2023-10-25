package com.mz.gateway.listener.redis;

import com.mz.common.constant.MzConstant;
import com.mz.gateway.route.RedisSubscribeRouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

@Configuration
public class MzRedisMessageListener {

    @Resource
    private RedisSubscribeRouteDefinitionRepository repository;

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(repository, new ChannelTopic(MzConstant.ROUTE_CHANNEL_KEY));
        return container;
    }

}
