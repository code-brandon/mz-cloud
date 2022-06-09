package com.mz.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.mz.common.mybatis.plugin.MzObjectWrapperFactoryConverter;
import com.mz.common.mybatis.plugin.MzSqlFilterArgumentResolver;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * What -- mz Mybatis 自动配置
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzMybatisAutoConfiguration
 * @CreateTime 2022/6/1 17:40
 */
@Configuration(proxyBeanMethods = false)
public class MzMybatisAutoConfiguration implements WebMvcConfigurer {

	/**
	 * SQL 过滤器避免SQL 注入
	 * @param argumentResolvers
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new MzSqlFilterArgumentResolver());
	}

	/**
	 * 审计字段自动填充
	 * @return {@link MetaObjectHandler}
	 */
	@Bean
	public MzMetaObjectHandler mzMetaObjectHandler() {
		return new MzMetaObjectHandler();
	}

	/**
	 * 旧版 分页插件配置
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
		paginationInterceptor.setOverflow(true);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		paginationInterceptor.setLimit(10);
		// 开启 count 的 join 优化,只针对部分 left join
		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
		return paginationInterceptor;
	}

	@Bean
	@ConfigurationPropertiesBinding
	public MzObjectWrapperFactoryConverter mzObjectWrapperFactoryConverter() {
		return new MzObjectWrapperFactoryConverter();
	}
}