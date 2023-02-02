package com.mz.auth.config;

import com.mz.common.encrypt.utils.MzCipherUtils;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.net.URL;
import java.security.KeyPair;

/**
 * What -- 配置非对称加密
 * <br>
 * Describe -- keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks
 * <br>
 * # Keytool 是一个java提供的证书管理工具
 * #    -alias：密钥的别名
 * #    -keyalg：使用的hash算法
 * #    -keypass：密钥的访问密码
 * #    -keystore：密钥库文件名，jwt.jks -> 生成的证书
 * #    -storepass：密钥库的访问密码
 * keytool -genkeypair -alias small-tools -keyalg RSA -keypass 123456 -keystore jwt.jks -storepass 123456
 * 查看公私钥：keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey <br>
 * 查看私钥：keytool -list -rfc -keystore jwt.jks -storepass  密钥库密码 <br>
 * @Package: com.mz.common.security.config
 * @ClassName: MzKeyPairConfig
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/8/29 19:54
 */
@Configuration
public class MzKeyPairConfig {

    @SneakyThrows
    @Bean("mzKeyPair")
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        // 秘钥位置
        URL jksPath = new ClassPathResource("key/jwt.jks").getURL();

        // 基于工厂拿到私钥
        return MzCipherUtils.getKeyPair(jksPath, "jwt", "mz_cloud");
    }

    /**
     * 在 JWT 编码的令牌值和 OAuth 身份验证信息（双向）之间转换的助手
     * @return JwtAccessTokenConverter
     */
    @Bean("mzJwtAccessTokenConverter")
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }
}
