package com.mz.gateway.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.mz.common.constant.MzConstant;
import com.mz.common.constant.enums.MzErrorCodeEnum;
import com.mz.common.core.exception.MzException;
import com.mz.common.redis.utils.MzRedisUtil;
import com.mz.common.utils.ConvertUtils;
import com.mz.common.utils.MzJacksonUtils;
import com.mz.common.utils.MzUtils;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MzVerifyCodeFilter implements GlobalFilter, Ordered {

    @Resource
    private MzRedisUtil mzRedisUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String captchaId = headers.getFirst(MzConstant.CAPTCHA_ID);
        log.debug("captchaId:{}", captchaId);

        if (!headers.containsKey(MzConstant.CAPTCHA_ID)){
            return chain.filter(exchange);
        }

        //文件上传不做处理
        String contentType = headers.getFirst("content-type");
        if (MzUtils.notEmpty(contentType) && contentType.contains("multipart/form-data")) {
            return chain.filter(exchange);
        }

        // 检查请求是否包含请求体
        boolean hasBody = headers.getContentLength() > 0 || headers.getContentType() != null;

        if (hasBody) {
            // 从请求体中获取参数
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        String body = new String(bytes, StandardCharsets.UTF_8);

                        Map<String, Object> requestBodyMap = new HashMap<>();
                        if (ContentType.APPLICATION_JSON.getMimeType().equals(contentType)) {
                            Map obj = MzJacksonUtils.toObj(body, Map.class);
                            requestBodyMap.putAll(obj);
                        } else {
                            Map<String, String> paramMap = HttpUtil.decodeParamMap(body, CharsetUtil.CHARSET_UTF_8);
                            requestBodyMap.putAll(paramMap);
                        }

                        log.debug("charBuffer =" + MzJacksonUtils.toJson(requestBodyMap));

                        processingVerifiyCode(request, captchaId, ConvertUtils.toStr(requestBodyMap.get("code")));

                        // 此处可以对请求体进行处理
                        exchange.getAttributes().put("cachedRequestBodyObject", body);
                        DataBuffer bodyDataBuffer = stringBuffer(body);
                        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

                        ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(request) {
                            @Override
                            public @NotNull Flux<DataBuffer> getBody() {
                                return bodyFlux;
                            }
                        };
                        return chain.filter(exchange.mutate().request(decorator).build());
                    });
        }
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return 0;
    }

    private void processingVerifiyCode(ServerHttpRequest request,String captchaId,String code){
            if (StrUtil.isEmpty(captchaId)) {
                throw new MzException(MzErrorCodeEnum.CAPTCHA_EMPTY_EXCEPTION,code);
            }
            String checkOkKey = MzConstant.CAPTCHA_PREFIX.concat(MzConstant.CAPTCHA_OK_KEY).concat(captchaId);
            String uuid = mzRedisUtil.getCacheObject(checkOkKey);
            if (StrUtil.isEmpty(uuid)) {
                throw new MzException(MzErrorCodeEnum.CAPTCHA_LAPSE_EXCEPTION,code);
            }
            if (!StrUtil.equalsIgnoreCase(uuid, code)) {
                throw new MzException(MzErrorCodeEnum.CAPTCHA_ERROR_EXCEPTION,code);
            }
            // 删除缓存里的验证码信息
            mzRedisUtil.delete(checkOkKey);
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }
}
