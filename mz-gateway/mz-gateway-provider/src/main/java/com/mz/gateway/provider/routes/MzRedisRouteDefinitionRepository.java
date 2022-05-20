package com.mz.gateway.provider.routes;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * What -- Redis 路由定义存储库
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzRedisRouteDefinitionRepository
 * @CreateTime 2022/5/20 21:14
 */
@Component
public class MzRedisRouteDefinitionRepository implements RouteDefinitionRepository {

    public static final String GATEWAY_ROUTES_PREFIX = "GETEWAY_ROUTES";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private Set<RouteDefinition> routeDefinitions = new HashSet<>();

    /**
     * 获取全部路由
     *
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        /**
         * 从redis 中 获取 全部路由,因为保存在redis ,mysql 中 频繁读取mysql 有可能会带来不必要的问题
         */
        routeDefinitions.clear();
        BoundHashOperations<String, String, String> boundHashOperations = stringRedisTemplate.boundHashOps(GATEWAY_ROUTES_PREFIX);
        Map<String, String> map = boundHashOperations.entries();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            routeDefinitions.add(JSON.parseObject(entry.getValue(), RouteDefinition.class));
        }
        return Flux.fromIterable(routeDefinitions);
    }

    /**
     * 添加路由方法
     *
     * @param route
     * @return
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
            routeDefinitions.add(routeDefinition);
            return Mono.empty();
        });
    }

    /**
     * 删除路由
     *
     * @param routeId
     * @return
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            List<RouteDefinition> routeDefinitionList = routeDefinitions.stream().filter(
                    routeDefinition -> StringUtils.equals(routeDefinition.getId(), id)
            ).collect(Collectors.toList());
            routeDefinitions.removeAll(routeDefinitionList);
            return Mono.empty();
        });
    }
}
