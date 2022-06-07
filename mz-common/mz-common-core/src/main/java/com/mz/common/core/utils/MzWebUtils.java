package com.mz.common.core.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import com.mz.common.core.constants.Constant;
import com.mz.common.core.exception.MzException;
import com.sun.istack.internal.NotNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * What -- Web 工具类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzWebUtils
 * @CreateTime 2022/5/24 17:44
 */
@Slf4j
@UtilityClass
@ConditionalOnClass(org.springframework.web.util.WebUtils.class)
public class MzWebUtils extends org.springframework.web.util.WebUtils {

	/**
	 * 判断是否ajax请求 spring ajax 返回含有 ResponseBody 或者 RestController注解
	 * @param handlerMethod HandlerMethod
	 * @return 是否ajax请求
	 */
	public boolean isBody(HandlerMethod handlerMethod) {
		ResponseBody responseBody = ClassUtils.getAnnotation(handlerMethod, ResponseBody.class);
		return responseBody != null;
	}

	/**
	 * 读取cookie
	 * @param name cookie name
	 * @return cookie value
	 */
	public String getCookieVal(String name) {
		if (MzWebUtils.getRequest().isPresent()) {
			return getCookieVal(MzWebUtils.getRequest().get(), name);
		}
		return null;
	}

	/**
	 * 读取cookie
	 * @param request HttpServletRequest
	 * @param name cookie name
	 * @return cookie value
	 */
	public String getCookieVal(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	/**
	 * 清除 某个指定的cookie
	 * @param response HttpServletResponse
	 * @param key cookie key
	 */
	public void removeCookie(HttpServletResponse response, String key) {
		setCookie(response, key, null, 0);
	}

	/**
	 * 设置cookie
	 * @param response HttpServletResponse
	 * @param name cookie name
	 * @param value cookie value
	 * @param maxAgeInSeconds maxage
	 */
	public void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAgeInSeconds);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/**
	 * 获取 HttpServletRequest
	 * @return {HttpServletRequest}
	 */
	public Optional<HttpServletRequest> getRequest() {
		return Optional
				.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
	}

	/**
	 * 获取 HttpServletResponse
	 * @return {HttpServletResponse}
	 */
	public HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * 返回json
	 * @param response HttpServletResponse
	 * @param result 结果对象
	 */
	public void renderJson(HttpServletResponse response, Object result) {
		renderJson(response, result, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 返回json
	 * @param response HttpServletResponse
	 * @param result 结果对象
	 */
	public void renderJson(HttpServletResponse response, Object result,int code) {
		renderJson(response, result, MediaType.APPLICATION_JSON_VALUE,code);
	}

	/**
	 * 返回json
	 * @param response HttpServletResponse
	 * @param result 结果对象
	 * @param contentType contentType
	 */
	public void renderJson(HttpServletResponse response, Object result, String contentType) {
		response.setCharacterEncoding(Constant.UTF8);
		response.setContentType(contentType);
		try (PrintWriter out = response.getWriter()) {
			out.append(JSONUtil.toJsonStr(result));
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 返回json
	 * @param response HttpServletResponse
	 * @param result 结果对象
	 * @param contentType contentType
	 */
	public void renderJson(HttpServletResponse response, Object result, String contentType,int code) {
		response.setCharacterEncoding(Constant.UTF8);
		response.setContentType(contentType);
		response.setStatus(code);
		try (PrintWriter out = response.getWriter()) {
			out.append(JSONUtil.toJsonStr(result));
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 从request 获取CLIENT_ID
	 * @return
	 */
	@SneakyThrows
	public String getClientId(ServerHttpRequest request) {
		String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		return splitClient(header)[0];
	}

	@SneakyThrows
	public String getClientId() {
		if (MzWebUtils.getRequest().isPresent()) {
			String header = MzWebUtils.getRequest().get().getHeader(HttpHeaders.AUTHORIZATION);
			return splitClient(header)[0];
		}
		return null;
	}

	@NotNull
	private static String[] splitClient(String header) {
		if (header == null || !header.startsWith(Constant.TOKEN_PREFIX)) {
			throw new MzException("请求头中client信息为空");
		}
		byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new MzException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, StandardCharsets.UTF_8);

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new MzException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}
