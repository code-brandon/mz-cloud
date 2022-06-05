package com.mz.sentinel.dashboard.rule.redis;

import com.alibaba.cloud.sentinel.datasource.config.DataSourcePropertiesConfiguration;
import com.alibaba.cloud.sentinel.datasource.config.RedisDataSourceProperties;
import com.alibaba.fastjson.JSONObject;
import com.mz.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.mz.sentinel.dashboard.properties.SentinelProperties;
import com.mz.sentinel.dashboard.rule.DynamicRulePublisher;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * What -- 自定义实现限流配置推送规则
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: FlowRuleRedisPublisher
 * @CreateTime 2022/6/4 18:05
 */
@Component("flowRuleRedisPublisher")
public class FlowRuleRedisPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {
    private final Logger logger = LoggerFactory.getLogger(FlowRuleRedisPublisher.class);
    @Autowired
    private SentinelProperties sentinelProperties;

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {

        DataSourcePropertiesConfiguration dataSource = sentinelProperties.getDatasource().get("redis");
        RedisDataSourceProperties redisDataSourceProperties = dataSource.getRedis();

        RedisURI redisURI = RedisURI.builder()
                .withHost(redisDataSourceProperties.getHost())
                .withPort(redisDataSourceProperties.getPort())
                .withPassword(redisDataSourceProperties.getPassword())
                .build();
        RedisClient redisClient = RedisClient.create(redisURI);

        StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();
        RedisPubSubCommands<String, String> subCommands = connection.sync();

        logger.info("Sentinel 向Redis推送流控规则 begin");
        if (rules == null){
            return;
        }
        subCommands.multi();
        subCommands.set(redisDataSourceProperties.getRuleKey()+app, JSONObject.toJSONString(rules));
        subCommands.publish(redisDataSourceProperties.getChannel()+app,JSONObject.toJSONString(rules));
        subCommands.exec();
        logger.info("Sentinel 向Redis推送流控规则 end");
    }
}