package com.mz.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.mz.common.mybatis.aspect.MzDataAuthAspectj;
import com.mz.common.mybatis.plugin.MzDataPermissionIntercept;
import com.mz.common.mybatis.plugin.MzObjectWrapperFactoryConverter;
import com.mz.common.mybatis.plugin.MzParameterInterceptor;
import com.mz.common.mybatis.plugin.MzSqlFilterArgumentResolver;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
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
@Import(MzDataAuthAspectj.class)
@Configuration(proxyBeanMethods = false)
public class MzMybatisAutoConfiguration implements WebMvcConfigurer {

	/**
	 * SQL 过滤器避免SQL 注入
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
	 * 新版 分页插件配置
	 * @return
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
		paginationInterceptor.setOverflow(true);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		paginationInterceptor.setMaxLimit(200L);
		// 开启 count 的 join 优化,只针对部分 left join
		paginationInterceptor.setOptimizeJoin(true);
		interceptor.addInnerInterceptor(paginationInterceptor);
		return interceptor;
	}

	/**
	 * 自定义Mybatis 插件 SQL美化
	 */
	@Bean
	@Profile(value = {"dev","test"})
	public MzParameterInterceptor mzMybatisLogInterceptor(){
		return new MzParameterInterceptor();
	}

	@Bean
	public MzDataPermissionIntercept mzDataPermissionIntercept(){
		return new MzDataPermissionIntercept();
	}

	@Bean
	@ConfigurationPropertiesBinding
	public MzObjectWrapperFactoryConverter mzObjectWrapperFactoryConverter() {
		return new MzObjectWrapperFactoryConverter();
	}

	@Bean
	public MzMybatisPostpositionHandler mzMybatisPostpositionHandler(){
		return new MzMybatisPostpositionHandler();
	}
}