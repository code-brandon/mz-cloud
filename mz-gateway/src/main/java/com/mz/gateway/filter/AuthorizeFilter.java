package com.mz.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.mz.common.core.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Order(-1) //过滤器的权重
@Slf4j
@Component
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        log.info("网关拦截到请求，其URI为：{}", uri);
        // 1.获取请求参数
        MultiValueMap<String, String> params = request.getQueryParams();
        log.debug("网关拦截到请求，其params为：{}", JSON.toJSONString(params.toSingleValueMap()));

        // 1.1. 清洗请求头中mz_from 参数
        request = request.mutate().headers(httpHeaders -> httpHeaders.remove(SecurityConstants.MZ_FROM)).build();

        // 2.获取Headers参数
        HttpHeaders headers = request.getHeaders();
        log.debug("网关拦截到请求，headers：{}", JSON.toJSONString(headers.toSingleValueMap()));
        // 放行
        return chain.filter(exchange.mutate().request(request.mutate().build()).build());
    }
}