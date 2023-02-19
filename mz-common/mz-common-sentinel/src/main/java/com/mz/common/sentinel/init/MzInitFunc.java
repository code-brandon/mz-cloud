package com.mz.common.sentinel.init;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.datasource.config.DataSourcePropertiesConfiguration;
import com.alibaba.cloud.sentinel.datasource.config.RedisDataSourceProperties;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

/**
 * What -- 拉取redis中的sentinel规则
 * <br>
 * Describe -- SpringBoot 启动后执行 将sentinel的规则从redis中拉取下来
 * <br>
 *
 * @Package: com.mz.common.sentinel.init
 * @ClassName: MzInitFunc
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/3 23:13
 */
public class MzInitFunc implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(MzInitFunc.class);

    @Autowired
    private SentinelProperties sentinelProperties;

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        DataSourcePropertiesConfiguration dataSource = sentinelProperties.getDatasource().get("redis");
        RedisDataSourceProperties redisDataSourceProperties = dataSource.getRedis();

        // TODO 手动实现转换器，框架会出现  c.a.c.s.d.converter.SentinelConverter    : converter can not convert rules because source is empty 警告
        Converter<String, List<FlowRule>> parser = source -> JSON.parseObject(source,new TypeReference<List<FlowRule>>() {});
        RedisConnectionConfig config = RedisConnectionConfig.builder()
                .withHost(redisDataSourceProperties.getHost())
                .withPort(redisDataSourceProperties.getPort())
                .withPassword(redisDataSourceProperties.getPassword())
                .build();
        // 流控规则
        ReadableDataSource<String, List<FlowRule>> redisDataSource = new RedisDataSource(
                config,
                redisDataSourceProperties.getRuleKey()+appName,
                redisDataSourceProperties.getChannel()+appName,
                parser
        );
        FlowRuleManager.register2Property(redisDataSource.getProperty());

        logger.info("{}：拉取sentinel规则成功",appName);
    }
}
