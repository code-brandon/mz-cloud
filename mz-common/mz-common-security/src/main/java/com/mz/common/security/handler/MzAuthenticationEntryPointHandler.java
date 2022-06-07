package com.mz.common.security.handler;

import com.alibaba.fastjson.JSON;
import com.mz.common.core.entity.R;
import com.mz.common.core.exception.MzCodeEnum;
import com.mz.common.core.utils.MzWebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * What -- Mz 身份验证入口点处理程序 （资源服务器）
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.security.handler
 * @ClassName: MzAuthenticationEntryPointHandler
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/7 17:42
 */
@Component
public class MzAuthenticationEntryPointHandler extends OAuth2AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        MzWebUtils.renderJson(response, JSON.toJSONString(R.error(MzCodeEnum.OAUTH_AUTH_EXCEPTION.getCode(), MzCodeEnum.OAUTH_AUTH_EXCEPTION.getMsg() + authException.getMessage())));
    }
}
