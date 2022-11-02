package com.xiaozheng.encrypt;

import com.xiaozheng.encrypt.utils.MzCipherUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Map;
import java.util.Objects;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.xiaozheng.encrypt
 * @ClassName: MzCipherUtilsTest
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/11/2 22:02
 */
@Slf4j
public class MzCipherUtilsTest {

    @Test
    public void test1(){
        Map<String, Object> stringObjectMap = MzCipherUtils.initRSAKey();
        log.info("\n{}" ,stringObjectMap.get("RSAPublicKey"));
    }

    @Test
    public void test2(){
        String rsaPublicKey = MzCipherUtils.getRSAPublicKey(MzCipherUtils.initRSAKey());
        log.info(rsaPublicKey);
    }

    @Test
    public void test3() throws IOException {
        // KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("key/jwt.jks"), "mz_cloud".toCharArray());
        // KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jwt", "mz_cloud".toCharArray());
        boolean b = MzCipherUtils.verifyCertificate(Objects.requireNonNull(this.getClass().getClassLoader().getResource("key/jwt.jks")).getPath(), "jwt", "mz_cloud");
        log.info("{}",b);
    }

    @Test
    public void test4() throws IOException {
        KeyPair keyPair = MzCipherUtils.getKeyPair(this.getClass().getClassLoader().getResource("key/jwt.jks").getPath(), "jwt", "mz_cloud");
        log.info("{}",keyPair);
    }



}
