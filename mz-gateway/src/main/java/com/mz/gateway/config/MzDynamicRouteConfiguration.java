package com.mz.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * What -- 动态路由配置类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: DynamicRouteAutoConfiguration
 * @CreateTime 2022/5/27 17:11
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class MzDynamicRouteConfiguration {

	/**
	 * 配置文件设置为 redis 加载为准
	 *
	 * @return
	 */
	@Bean
	public PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator() {
		return new PropertiesRouteDefinitionLocator(new GatewayProperties());
	}

	/**
	 * 编码注入网关路由
	 */
	// @Bean
	// protected RouteLocator routeLocator(RouteLocatorBuilder builder) {
	// 	return builder.routes().route(
	// 			// 请求字服务路径以product/**
	// 			r -> r.path("/product/**", "/catgory/**")
	// 					.uri("lb://api-product")
	// 					.id("product")
	// 	).build();
	// }
}