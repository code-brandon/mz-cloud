package com.mz.auth.handler;

import com.alibaba.fastjson.JSON;
import com.mz.common.core.entity.R;
import com.mz.common.core.exception.MzCodeEnum;
import com.mz.common.core.utils.MzWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		MzWebUtils.renderJson(response, JSON.toJSONString(R.error(MzCodeEnum.OAUTH_EXCEPTION.getCode(), MzCodeEnum.OAUTH_EXCEPTION.getMsg() + exception.getMessage())));
	}
}
