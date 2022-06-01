package com.mz.common.core.config;

import com.mz.common.core.utils.MinioUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 小政同学 QQ:xiaozheng666888@qq.com
 * @Date: 2020/12/01/17:05
 * @Description: 创建Bean
 */
@Configuration
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix ="minio.config")
// 判断yml配置文件中是否有改属性，true加载Bean
@ConditionalOnProperty(name ="minio.config",matchIfMissing = false)
public class MinioConfig {
 

  private String endpoint;
  private String bucketName;
  private String accessKey;
  private String secretKey;
 
  @Bean
  public MinioUtils creatMinioClient() {
    log.debug("{}, {}, {}, {}",endpoint, bucketName, accessKey, secretKey);
    return new MinioUtils(endpoint, bucketName, accessKey, secretKey);
  }
 
}