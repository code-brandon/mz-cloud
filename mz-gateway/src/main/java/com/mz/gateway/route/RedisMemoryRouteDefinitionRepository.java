package com.mz.gateway.route;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.mz.common.core.constants.Constant;
import com.mz.common.redis.utils.MzRedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.gateway.route
 * @ClassName: RedisMemoryRouteDefinitionRepository
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/27 15:39
 */
@Component
@AllArgsConstructor
@Slf4j
public class RedisMemoryRouteDefinitionRepository implements RouteDefinitionRepository, ApplicationEventPublisherAware {

    private final MzRedisUtil mzRedisUtil;
    private ApplicationEventPublisher publisher;
    private final GatewayProperties gatewayProperties;

    /**
     * 动态路由入口
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> definitionList = new ArrayList<>();
        Boolean aBoolean = mzRedisUtil.getoRedisTemplate().hasKey(Constant.ROUTE_KEY);
        if (!aBoolean) {
            RouteDefinition routeDefinition = new RouteDefinition();
            routeDefinition.setId("null");
            try {
                routeDefinition.setUri(new URI("lb://api-product"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            definitionList.add(routeDefinition);
            return Flux.fromIterable(definitionList);
        }
        definitionList = mzRedisUtil.getoRedisTemplate().opsForHash().values(Constant.ROUTE_KEY);
        gatewayProperties.setRoutes(definitionList);
        log.info("redis 中路由定义条数： {}， definitionList={}", definitionList.size(), JSON.toJSONString(definitionList));
        return Flux.fromIterable(definitionList);
    }

    private void refreshRoutes() {
        log.info("保存路由信息{}", DateUtil.formatBetween(System.currentTimeMillis()));
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }


    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            log.info("保存路由信息{}", r);
            mzRedisUtil.getoRedisTemplate().opsForHash().put(Constant.ROUTE_KEY,r.getId(),r);
            refreshRoutes();
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            log.info("删除路由信息={}", id);
            mzRedisUtil.getoRedisTemplate().opsForHash().delete(Constant.ROUTE_KEY,id);
            refreshRoutes();
        });
        return Mono.empty();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
