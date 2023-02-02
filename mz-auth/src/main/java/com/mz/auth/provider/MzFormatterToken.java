package com.mz.auth.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.JacksonUtils;
import com.mz.common.core.entity.R;
import com.mz.common.utils.MzWebUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * What -- 格式化程序令牌
 * <br>
 * Describe -- 将返回的<br>
 * <pre class="code">
 * {
 * 		"token_type": "bearer",
 * 		"access_token": "ce10568f-170a-4440-9384-702e38d30b09",
 * 		"refresh_token": "15b20dfc-8a61-48ba-924f-37f9982b64fc",
 * 		"scope": "all",
 * 		"expires_in": 17990
 *  }
 * </pre>
 * 转换为：
 * <pre class="code">
 * {
 * 	"code": 0,
 * 	"data": {
 * 		"token_type": "bearer",
 * 		"access_token": "ce10568f-170a-4440-9384-702e38d30b09",
 * 		"refresh_token": "15b20dfc-8a61-48ba-924f-37f9982b64fc",
 * 		"scope": "all",
 * 		"expires_in": 17990
 *        },
 * 	"message": "操作成功！"
 * }
 * </pre>
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzFormatterToken
 * @CreateTime 2022/9/29 23:55
 */
public class MzFormatterToken implements HandlerMethodReturnValueHandler {

    private static final String POST_ACCESS_TOKEN = "postAccessToken";

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        // 判断方法名是否是 oauth2 的token 接口，是就处理
        return POST_ACCESS_TOKEN.equals(Objects.requireNonNull(returnType.getMethod()).getName());
    }

    /**
     * 获取到返回值然后使用 R对象统一包装
     * @param returnValue the value returned from the handler method
     * @param returnType the type of the return value. This type must have
     * previously been passed to {@link #supportsReturnType} which must
     * have returned {@code true}.
     * @param container the ModelAndViewContainer for the current request
     * @param request the current request
     */
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer container, NativeWebRequest request) {
        ResponseEntity responseEntity = (ResponseEntity) returnValue;
        Object body = responseEntity.getBody();

        HttpServletResponse response = request.getNativeResponse(HttpServletResponse.class);
        assert response != null;
        // TODO JSON.parse(JacksonUtils.toJson(body))) 原因：使其转换为JSON时符合原有格式，否则格式与包装前data中的格式不能对应
        MzWebUtils.renderJson(response, R.ok(JSON.parse(JacksonUtils.toJson(body))));
    }


}  