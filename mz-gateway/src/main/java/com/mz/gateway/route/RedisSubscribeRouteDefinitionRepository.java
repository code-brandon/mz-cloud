package com.mz.gateway.route;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.common.constant.MzConstant;
import com.mz.common.gateway.entity.MzGatewayRoute;
import com.mz.common.redis.utils.MzRedisUtil;
import com.mz.common.utils.MzJacksonUtils;
import com.mz.common.utils.MzUtils;
import com.mz.gateway.utils.RouteConvert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

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
@Component
@AllArgsConstructor
@Slf4j
public class RedisSubscribeRouteDefinitionRepository extends MessageListenerAdapter implements RouteDefinitionRepository, ApplicationEventPublisherAware {

    private final MzRedisUtil mzRedisUtil;
    private ApplicationEventPublisher publisher;
    @Qualifier("mzObjectMapper")
    private ObjectMapper objectMapper;

    private final GatewayProperties gatewayProperties;
    private final Map<String, RouteDefinition> routes = synchronizedMap(new LinkedHashMap<>());

    /**
     * RouteDefinitionLocator里面的getRouteDefinitions方法是每隔几秒钟就会执行一次的。
     * 配置修改的情况比较少，等监听到有修改的时候再做更新。
     */
    private static volatile boolean IS_UPDATE = true;
    /**
     * 动态路由入口,获取路由定义
     * @return
     */

    /**
     * 刷新路由
     */
    private void refreshRoutes() {
        IS_UPDATE = true;
        log.debug("刷新路由信息{}", DateUtil.formatBetween(System.currentTimeMillis()));
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 动态路由入口,获取路由定义
     * @return
     */
    @Override
    public synchronized Flux<RouteDefinition>  getRouteDefinitions() {
        if (IS_UPDATE){
            List<Object> hValues = mzRedisUtil.hValues(MzConstant.ROUTE_KEY);
            hValues.forEach(o->{
                try {
                    MzGatewayRoute  mzGatewayRoute = MzJacksonUtils.toObj((String) o, MzGatewayRoute.class);
                    if (!MzUtils.isEmpty(mzGatewayRoute)){
                        RouteDefinition route = RouteConvert.toRouteDefinition(mzGatewayRoute);
                        routes.put(route.getId(),route);
                    }
                } catch (Exception e) {
                    try {
                        RouteDefinition routeDefinition = objectMapper.readValue((String) o, RouteDefinition.class);
                        if (!MzUtils.isEmpty(routeDefinition)){
                            RouteConvert.renameRouteArgs(routeDefinition);
                            routes.put(routeDefinition.getId(),routeDefinition);
                        }
                    } catch (JsonProcessingException ex) {
                        log.error("序列化失败：{}",o);
                    }
                }
            });
            gatewayProperties.setRoutes(Arrays.asList(routes.values().toArray(new RouteDefinition[0])));
            if (MzUtils.notEmpty(routes)){
                IS_UPDATE = false;
            }
        }
        log.debug("redis 中路由定义条数： {}，更新状态：{}", routes.size(), IS_UPDATE);
        return Flux.fromIterable(routes.values());
    }


    /**
     * 保存路由信息
     * @param route
     * @return
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return Mono.empty();
    }

    /**
     * 删除路由信息
     * @param routeId
     * @return
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return Mono.empty();
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.debug("动态路由订阅信息：{}",message);
        if ("1".equals(String.valueOf(message))) {
            refreshRoutes();
        } else {
            log.info("网关路由信息:{}", MzJacksonUtils.toJson(routes));
        }
    }
}
