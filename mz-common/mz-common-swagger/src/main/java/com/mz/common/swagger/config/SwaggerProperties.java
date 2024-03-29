package com.mz.common.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * What -- Swagger 配置文件实体类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: SwaggerProperties
 * @CreateTime 2022/5/24 16:08
 */
@Data
@Configuration
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {
    public static final String PREFIX="springfox.swagger";

    /**
     * 包
     */
    private String basePackage;

    /**
     * api 前缀
     */
    private String path;

    /**
     * 作者相关信息
     */
    private Author author;

    /**
     * API的相关信息
     */
    private ApiInfo apiInfo;

    @Data
    public static class ApiInfo{
        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String license;
        private String licenseUrl;
    }
    @Data
    public static class Author{
        private String name;
        private String email;
        private String url;
    }
}
