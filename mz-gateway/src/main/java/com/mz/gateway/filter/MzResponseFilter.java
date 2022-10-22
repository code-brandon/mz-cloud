package com.mz.gateway.filter;

import com.mz.common.core.context.MzDefaultContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * What -- Mz网关响应拦截  放在最后面
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
public class MzResponseFilter implements GlobalFilter , Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();

        // 请求结束，开始返回销毁局部线程
        MzDefaultContextHolder.CONTEXT_HOLDER.remove();
        // 放行
        return chain.filter(exchange.mutate().response(response).build());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}