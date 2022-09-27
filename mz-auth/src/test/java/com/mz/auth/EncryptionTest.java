package com.mz.auth;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * What -- 加密测试
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.auth
 * @ClassName: EncryptionTest
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/8/30 20:33
 */
public class EncryptionTest {

    private static RSAPrivateKey rsaPrivateKey;

    private static RSAPublicKey rsaPublicKey;

    @BeforeAll
    static void getKeyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                // 秘钥位置
                new ClassPathResource("key/jwt.jks"),
                // 密码库密码
                "mz_cloud".toCharArray()
        );
        // 基于工厂拿到私钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jwt", "mz_cloud".toCharArray());

        // 转化为rsa私钥
        rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 转化为rsa公钥
        rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
    }

    @Test
    void main() {

        RsaVerifier rsaVerifier = new RsaVerifier(rsaPublicKey);
        RsaSigner rsaSigner = new RsaSigner(rsaPrivateKey);

        RSAKey rsaKey = new RSAKey.Builder(rsaPublicKey).build();
        Map<String, Object> objectMap = new JWKSet(rsaKey).toJSONObject();
        System.out.println("objectMap = " + objectMap);
        // 3、生成jwt
        Map<String, String> map = Maps.newHashMap();
        map.put("username", "zq");
        map.put("password", "123456");
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(rsaPrivateKey));
        String jwtEncoded = jwt.getEncoded();
        System.out.println("jwtEncoded:" + jwtEncoded);
        String claims = jwt.getClaims();
        System.out.println("claims:" + claims);
    }

    @Test
    public void testParseJwt() throws Exception {
        // 基于公钥去解析jwt
        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoienEifQ.kIQYBVH36UeC2J6dEq-IPyBiFfXinhpybEVcTxXgcBNDt6QulrsJkT-ovYlIuDeiKWqS6lBwdgj9Yrbc_fOVHnc7fHO_epyi3Kyj09ZPthNA9nNZPuDAKQxgl0DXGn_uR5saUvUlCySfAj5RQfbl335Kog_YGdd-GbD_q0-4eZu5CnmeaRHi1RPx4X5DuCScaFgpNDzsBsIv371kvCZOGBK335bdv1w1opRl7NtQxwleaALEQ0M8icMekc8uPEc9Affl9bFZ6vAz8bUmBCti1WWYEgQkHtC5rZa6zTTiGxqDN5PgrdXdwxPK3iSYTRJclAvF6Hfb0SHs6LLTIezX6A";
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvr5/ov1jYooUE/l704vQ\n" +
                "EZ9oFqH4H0NA50alXGNc872pWSqs17T/9uVtfoKDtZJ+5cgvIlC8JriW+4SfSzuS\n" +
                "AuJMDwlFT8JKaaQSkyGgR320smbxxZKGZ59ysR3FsaPWDVSlqyJB2gHVIdpXbNFc\n" +
                "KJtJ1yXBHH2IJyEmZCYH2PxgiZUBTt65tJ1wPWNOrxrJayz2Jr7cLB+vLhVNiUug\n" +
                "a2q6W1CeGYrgoU/wjqb1mYVysfqQj35TXmsqqirQmrzFfnRbVgSfkWxzHAa1chl4\n" +
                "JBnVmUxca0gWIoT48tb9hLQ5SXaPlkiA2Mg64Wpk8tLZIGijtjLwn2yC6/WNpNg7\n" +
                "RwIDAQAB\n" +
                "-----END PUBLIC KEY-----";
        // 解析令牌
        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));
        // 获取负载
        String claims = token.getClaims();
        System.out.println(claims);
    }
}
