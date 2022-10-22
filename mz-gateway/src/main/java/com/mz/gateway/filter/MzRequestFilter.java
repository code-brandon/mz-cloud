package com.mz.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.mz.common.core.constants.SecurityConstants;
import com.mz.common.core.context.MzDefaultContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * What -- Mz网关请求拦截
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzRequestFilter
 * @CreateTime 2022/6/8 19:23
 */
@Slf4j
@Component
public class MzRequestFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        log.info("网关拦截到请求，其URI为：{}", uri);
        // 1.获取请求参数
        MultiValueMap<String, String> params = request.getQueryParams();
        log.info("网关拦截到请求，其params为：{}", JSON.toJSONString(params.toSingleValueMap()));
        // 1.1. 清洗请求头中mz_from 参数
        request = request.mutate().headers(httpHeaders -> httpHeaders.remove(SecurityConstants.MZ_FROM)).build();

        // 2.获取Headers参数
        HttpHeaders headers = request.getHeaders();
        log.info("网关拦截到请求，headers：{}", JSON.toJSONString(headers.toSingleValueMap()));

        MzDefaultContextHolder.CONTEXT_HOLDER.get().put("env", headers.get("env"));
        // 放行
        return chain.filter(exchange.mutate().request(request.mutate().build()).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}