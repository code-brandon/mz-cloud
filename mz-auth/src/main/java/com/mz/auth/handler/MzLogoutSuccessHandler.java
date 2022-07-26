package com.mz.auth.handler;

import com.mz.common.core.constants.Constant;
import com.mz.common.core.entity.R;
import com.mz.common.core.utils.MzWebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * What -- Mz 注销成功处理程序
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzLogoutSuccessHandler
 * @CreateTime 2022/6/22 13:12
 */
@Component
public class MzLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 将子系统的cookie删掉
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        String authHeader = request.getHeader(Constant.AUTHORIZATION);
        if (authHeader != null) {
            String tokenValue = authHeader.replace(Constant.TOKEN_PREFIX,"").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            if (Objects.nonNull(accessToken)) {
                tokenStore.removeAccessToken(accessToken);
                OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
                tokenStore.removeRefreshToken(refreshToken);
            }
        }
        // super.handle(request, response, authentication);
        MzWebUtils.renderJson(response, R.ok("注销成功"));
    }
}
