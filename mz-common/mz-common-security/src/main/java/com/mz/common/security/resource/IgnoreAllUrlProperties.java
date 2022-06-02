package com.mz.common.security.resource;

import cn.hutool.core.util.ReUtil;
import com.mz.common.security.annotation.Ignore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * What -- 资源服务器对外直接暴露URL,如果设置contex-path 要特殊处理
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: IgnoreAllUrlProperties
 * @CreateTime 2022/6/2 16:45
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
// 取决于 SpEL 表达式的值的条件元素的配置注释
@ConditionalOnExpression("!'${security.oauth2.client.ignore-urls}'.isEmpty()")
@ConfigurationProperties(prefix = "security.oauth2.client")
public class IgnoreAllUrlProperties implements InitializingBean {

	private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

	private final WebApplicationContext applicationContext;

	@Getter
	@Setter
	private List<String> ignoreUrls = new ArrayList<>();

	@Override
	public void afterPropertiesSet() {
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> mapHandle = mapping.getHandlerMethods();

		mapHandle.keySet().forEach(info -> {
			HandlerMethod handlerMethod = mapHandle.get(info);

			// 获取方法上边的注解 替代path variable 为 *
			Ignore method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Ignore.class);
			Optional.ofNullable(method).ifPresent(inner -> info.getPatternsCondition().getPatterns()
					.forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));

			// 获取类上边的注解, 替代path variable 为 *
			Ignore clazz = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Ignore.class);
			Optional.ofNullable(clazz).ifPresent(inner -> info.getPatternsCondition().getPatterns()
					.forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
		});
		log.info("当前应用所忽略的APi地址为：{}",ignoreUrls);
	}

}