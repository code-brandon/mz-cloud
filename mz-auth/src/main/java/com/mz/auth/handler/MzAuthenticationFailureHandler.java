package com.mz.auth.handler;

import cn.hutool.http.HttpUtil;
import com.mz.common.utils.MzWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * What -- Mz 身份验证失败处理程序
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzAuthenticationFailureHandler
 * @CreateTime 2022/6/7 19:20
 */
@Slf4j
@Component
public class MzAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws ServletException, IOException {

		log.debug("表单登录失败:{}", exception.getLocalizedMessage());
		String url = HttpUtil.encodeParams(String.format("/token/login?error=%s", exception.getMessage()), StandardCharsets.UTF_8);
		MzWebUtils.getResponse().sendRedirect(url);

		// if (request.getMethod().equals(HttpMethod.POST.name()) && "/login".equals(request.getRequestURL())) {
		// 	MzWebUtils.renderJson(response, R.error(MzErrorCodeEnum.OAUTH_AUTH_EXCEPTION.getCode(), MzErrorCodeEnum.OAUTH_AUTH_EXCEPTION.getMsg() + exception.getMessage()));
		// }else {
		// 	SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler("/oauth/login");
		// 	failureHandler.onAuthenticationFailure(request, response, exception);
		// }
	}
}
