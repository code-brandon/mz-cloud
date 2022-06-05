package com.mz.sentinel.dashboard.rule.redis;

import com.alibaba.cloud.sentinel.datasource.config.DataSourcePropertiesConfiguration;
import com.alibaba.cloud.sentinel.datasource.config.RedisDataSourceProperties;
import com.alibaba.fastjson.JSONObject;
import com.mz.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.mz.sentinel.dashboard.properties.SentinelProperties;
import com.mz.sentinel.dashboard.rule.DynamicRuleProvider;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * What -- 自定义实现基于redis的流控拉取规则
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: FlowRuleRedisProvider
 * @CreateTime 2022/6/4 18:05
 */
@Component("flowRuleRedisProvider")
public class FlowRuleRedisProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {
    private final Logger logger = LoggerFactory.getLogger(FlowRuleRedisProvider.class);
    @Autowired
    private SentinelProperties sentinelProperties;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {

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
        logger.info("Sentinel 从Redis拉取流控规则 begin");
        String value = (String) subCommands.get(redisDataSourceProperties.getRuleKey() + appName);
        if (StringUtils.isEmpty(value)){
            return new ArrayList<>();
        }
        logger.info("Sentinel 从Redis拉取流控规则 end");
        return JSONObject.parseArray(value,FlowRuleEntity.class);
    }
}