package com.mz.gateway.route;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.mz.common.constant.MzConstant;
import com.mz.common.redis.utils.MzRedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * What -- Redis 内存路由定义库
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.gateway.route
 * @ClassName: RedisMemoryRouteDefinitionRepository
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/27 15:39
 */
// @Component
@AllArgsConstructor
@Slf4j
public class RedisMemoryRouteDefinitionRepository implements RouteDefinitionRepository, ApplicationEventPublisherAware {

    private final MzRedisUtil mzRedisUtil;
    private ApplicationEventPublisher publisher;
    private final GatewayProperties gatewayProperties;

    /**
     * 动态路由入口,获取路由定义
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> definitionList = new ArrayList<>();
        Boolean aBoolean = mzRedisUtil.getoRedisTemplate().hasKey(MzConstant.ROUTE_KEY);
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
        definitionList = mzRedisUtil.getoRedisTemplate().opsForHash().values(MzConstant.ROUTE_KEY);
        gatewayProperties.setRoutes(definitionList);
        log.debug("redis 中路由定义条数： {}， definitionList={}", definitionList.size(), JSON.toJSONString(definitionList));
        return Flux.fromIterable(definitionList);
    }

    /**
     * 刷新路由
     */
    private void refreshRoutes() {
        log.debug("刷新路由信息{}", DateUtil.formatBetween(System.currentTimeMillis()));
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }


    /**
     * 保存路由信息
     * @param route
     * @return
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            log.debug("保存路由信息{}", r);
            mzRedisUtil.getoRedisTemplate().opsForHash().put(MzConstant.ROUTE_KEY,r.getId(),r);
            refreshRoutes();
            return Mono.empty();
        });
    }

    /**
     * 删除路由信息
     * @param routeId
     * @return
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            log.debug("删除路由信息={}", id);
            mzRedisUtil.getoRedisTemplate().opsForHash().delete(MzConstant.ROUTE_KEY,id);
            refreshRoutes();
        });
        return Mono.empty();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
