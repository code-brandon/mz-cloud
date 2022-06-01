package com.mz.gateway.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
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
        log.info("网关拦截到请求，其URI为：{}",uri);
        // 1.获取请求参数
        MultiValueMap<String, String> params =request.getQueryParams();
        log.info("网关拦截到请求，其params为：{}", JSON.toJSONString(params.toSingleValueMap()));

        // 2.获取authorization参数
        HttpHeaders headers = request.getHeaders();
        String auth = headers.getFirst("authorization");
        log.info("网关拦截到请求，authorization：{}",auth);

        // 3.校验
        if (!StringUtils.isEmpty(auth) || uri.getPath().contains("/api-docs")) {
            // 放行
            return chain.filter(exchange);
        }
        // 4.拦截
        // 4.1.禁止访问，设置状态码
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        // 4.2.结束处理
        return exchange.getResponse().setComplete();
    }
}