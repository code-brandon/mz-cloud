package com.mz.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mz.common.constant.MzConstant;
import com.mz.common.constant.SecurityConstants;
import com.mz.common.core.context.MzDefaultContextHolder;
import com.mz.common.utils.IPSearcherUtils;
import com.mz.common.utils.MzWebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
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
@RequiredArgsConstructor
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
        // 3.获取有用的Headers参数
        JSONObject jsonHeaders = new JSONObject();
        jsonHeaders.put("host", headers.getHost().toString());
        jsonHeaders.put("sec-ch-ua", headers.getFirst("sec-ch-ua"));
        jsonHeaders.put("user-agent", headers.getFirst("User-Agent"));
        jsonHeaders.put("ip", MzWebUtils.getRemoteAddr(request));
        jsonHeaders.put("ip-address", IPSearcherUtils.searcher(jsonHeaders.getString("ip")));
        jsonHeaders.put("accept-language", headers.getFirst("Accept-Language"));
        log.info("网关拦截到请求，headers：{}", jsonHeaders.toJSONString());

        boolean isEnv = headers.containsKey(MzConstant.GATEWAY_ENV);
        if (isEnv) {
            MzDefaultContextHolder.CONTEXT_HOLDER.get().put(MzConstant.GATEWAY_ENV, headers.get(MzConstant.GATEWAY_ENV));
        }
        // 放行
        return chain.filter(exchange.mutate().request(request.mutate().build()).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}