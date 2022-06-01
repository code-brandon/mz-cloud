package com.mz.common.swagger.config;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;


/**
 * What -- Swagger 配置类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: SwaggerConfig
 * @CreateTime 2022/5/24 16:08
 */
@Configuration
@Slf4j
@ConditionalOnBean({SwaggerProperties.class })
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties properties;

    @Bean
    public Docket createRestApi() {
        log.info("SwaggerConfig 加载----配置文件信息：{}",properties);
        Assert.notNull(properties, "SwaggerProperties 为空 请配置 Swagger文档信息");
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .pathMapping(StringUtils.isEmpty(properties.getPath()) ? "" : properties.getPath())
                // 授权信息
                .securitySchemes(securitySchemes())
                // 全局授权信息
                .securityContexts(securityContexts())
                .select()
                .apis(StringUtils.isEmpty(properties.getBasePackage()) ? RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class) : RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(PathSelectors.any())
                .build()/*.forCodeGeneration(true)*/
                // 支持的通信协议
                .protocols(new HashSet<String>(Arrays.asList("http", "https")));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(properties.getApiInfo().getTitle())
                .description(properties.getApiInfo().getDescription())
                .contact(new Contact(properties.getAuthor().getName(), properties.getAuthor().getUrl(), properties.getAuthor().getEmail()))
                .version(properties.getApiInfo().getVersion())
                .build();
    }

    private List<SecurityScheme> securitySchemes(){

        List<ApiKey> result = new ArrayList<>();
        //添加OAuth2的令牌认证
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        result.add(apiKey);
        return Collections.singletonList(apiKey);
    }


    private HttpAuthenticationScheme tokenScheme() {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("Authorization",
                                new AuthorizationScope[]{new AuthorizationScope("global", "Authorization")}))).build()
        );
    }

}