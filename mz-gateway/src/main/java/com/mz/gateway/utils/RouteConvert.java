package com.mz.gateway.utils;

import cn.hutool.core.bean.BeanUtil;
import com.mz.common.gateway.entity.MzFilterDefinition;
import com.mz.common.gateway.entity.MzGatewayRoute;
import com.mz.common.gateway.entity.MzPredicateDefinition;
import com.mz.common.gateway.utils.MzRouteNameUtils;
import com.mz.common.utils.MzUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 路由器转换工具类
 * @Date: 2021/10/21
 */
public class RouteConvert {

    private RouteConvert(){
    }

    /**
     * 转换为 RouteDefinition
     *
     * @param mzGatewayRoute
     * @return
     */
    public static RouteDefinition toRouteDefinition(MzGatewayRoute mzGatewayRoute) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(mzGatewayRoute.getRouteId());
        routeDefinition.setOrder(mzGatewayRoute.getOrder());
        routeDefinition.setUri(URI.create(mzGatewayRoute.getRouteUri()));
        if (!MzUtils.isEmpty(mzGatewayRoute.getPredicates())) {
            List<PredicateDefinition> predicates = BeanUtil.copyToList(mzGatewayRoute.getPredicates(),PredicateDefinition.class);
            routeDefinition.setPredicates(predicates);
        }
        if (!MzUtils.isEmpty(mzGatewayRoute.getFilters())) {
            List<FilterDefinition> filters = BeanUtil.copyToList(mzGatewayRoute.getFilters(),FilterDefinition.class);
            routeDefinition.setFilters(filters);
        }
        routeDefinition.setMetadata(mzGatewayRoute.getMetadata());
        renameRouteArgs(routeDefinition);
        return routeDefinition;
    }


    /**
     * 转换为路由数据库实体类
     *
     * @param routeDefinition
     * @return
     */
    public static MzGatewayRoute toMzGatewayRoute(RouteDefinition routeDefinition) {
        MzGatewayRoute mzGatewayRoute = new MzGatewayRoute();
        mzGatewayRoute.setRouteId(routeDefinition.getId());
        mzGatewayRoute.setRouteUri(routeDefinition.getUri().toString());
        List<MzPredicateDefinition> predicates = BeanUtil.copyToList(routeDefinition.getPredicates(),MzPredicateDefinition.class);
        mzGatewayRoute.setPredicates(predicates);
        List<MzFilterDefinition> filters = BeanUtil.copyToList(routeDefinition.getFilters(),MzFilterDefinition.class);
        mzGatewayRoute.setFilters(filters);
        mzGatewayRoute.setMetadata(routeDefinition.getMetadata());
        mzGatewayRoute.setOrder(routeDefinition.getOrder());
        return mzGatewayRoute;
    }


    public static void renameRouteArgs(RouteDefinition routeDefinition) {
        List<FilterDefinition> filters = routeDefinition.getFilters();
        List<PredicateDefinition> predicates = routeDefinition.getPredicates();
        for (FilterDefinition filter : filters) {
            String[] values =  Optional.ofNullable(filter.getArgs()).orElse(new LinkedHashMap<>()).values().toArray(new String[0]);
            filter.setArgs(MzRouteNameUtils.generateName(values));
        }
        for (PredicateDefinition predicate : predicates) {
            String[] values = Optional.ofNullable(predicate.getArgs()).orElse(new LinkedHashMap<>()).values().toArray(new String[0]);
            predicate.setArgs(MzRouteNameUtils.generateName(values));
        }
    }
}
