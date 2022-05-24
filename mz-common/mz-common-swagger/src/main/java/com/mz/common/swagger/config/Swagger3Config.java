package com.mz.common.swagger.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;


/**
 * What -- Swagger3 配置类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: Swagger3Config
 * @CreateTime 2022/5/24 16:08
 */
@Configuration
@ConditionalOnBean({SwaggerProperties.class })
public class Swagger3Config {

    @Autowired
    private SwaggerProperties properties;

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.OAS_30)
                .groupName("默认")
                .apiInfo(apiInfo())
                // 授权信息
                .securitySchemes(securitySchemes())
                // 全局授权信息
                .securityContexts(securityContexts())
                .select()
                .apis(Objects.isNull(properties) ? RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class) : RequestHandlerSelectors.basePackage(properties.getBasePackage()))
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