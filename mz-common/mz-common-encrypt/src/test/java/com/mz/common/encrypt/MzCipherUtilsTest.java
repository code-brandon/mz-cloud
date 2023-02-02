package com.mz.common.encrypt;

import com.mz.common.encrypt.utils.MzCipherUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.encrypt
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

    @SneakyThrows
    @Test
    public void test5(){
        String puK = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVJSH9pH6xX7GoFXFIfYAt-q5Jc_V0yIjzmhz3XnLNqpyqQ4S8ZzDg2eKRK7UHCpV1mGHvaDcItiy6O6x-gxvnU1yEGuy7UZ-W88_xIWrvrvJuAh9iXAopTTtW7bq0ZssdfMW5ZIzkouSBjlMNGxrrpZgnJS7vs_OLg-0adbwzrQIDAQAB";
        String prK = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJUlIf2kfrFfsagVcUh9gC36rklz9XTIiPOaHPdecs2qnKpDhLxnMODZ4pErtQcKlXWYYe9oNwi2LLo7rH6DG-dTXIQa7LtRn5bzz_Ehau-u8m4CH2JcCilNO1bturRmyx18xblkjOSi5IGOUw0bGuulmCclLu-z84uD7Rp1vDOtAgMBAAECgYBfh1Y7OTZw9AM9zuYtcT09thgWMjDg6WVW6rps81EDTKlmITMO3eWfz89f_qfH586NcNoh1xWQ_eauLnSw39dpnEagKKsTY9nEwa5cfkQdTS4yAWuOmr5ToQOwC8ZXPsGGFfNvlU04T3MkyEw8gxTttkGjJP8cY4AOJGQVxFCb_QJBAMg__UAc2D2U4GH3J4qJHf0kF14XcNfhWR-PUzXYpBVdNk_YoTY5uX4ZoprGKrUHBJviW6XFvL5-JdScq8oejZ8CQQC-qti9EV-J2_tunuegjP2T_82I7O7DkqbYpMTgL6sDlqYKL3r2EqLARE96yULuYmYHCW2pPuDxAQB-l10oruMzAkAChhvxBOwMyqWRVoYfDt9b42qP-wenEOYIqcvIr_RReJ1IoFhX0J5v4m7UOI8tPOtn85BoJHfehsR_S4I2x-_NAkEAkJV7r7vZ5--R9au4V84yiKVlFd0dnd66ePRRevOKyjjWBklFBA7TrdrfLmwqPh0N424p4zqUVw18c4KSmUQtcwJANkxCz5QyHgN_Wl9xbLCN1nTMpTQAYCMDkCRnsgRTyJsNJGwqz4O5CKOwnPhiGZNA-pn1DxkXK6FlW97uCs2skg";

        PublicKey publicKey= MzCipherUtils.getStringPublicKey(puK);
        KeyFactory keyFactory = KeyFactory.getInstance(MzCipherUtils.KEY_ALGORITHM);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        String input = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        byte[] inputArray = input.getBytes();
        int inputLength = inputArray.length;
        System.out.println("加密字节数：" + inputLength);
        // 最大加密字节数，超出最大字节数需要分组加密
        int MAX_ENCRYPT_BLOCK = 117;
        // 标识
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache = {};
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }
        String s2 = Base64.encodeBase64String(resultBytes);
        // String s = MzTranscodeUtils.byteArrayToBase64Str(cipher.doFinal(input.getBytes()));
        log.info("{}",s2);

        String str = s2;
        PrivateKey privateKey = MzCipherUtils.getStringPrivateKey(prK);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        inputArray = Base64.decodeBase64(str.getBytes("UTF-8"));
        inputLength = inputArray.length;
        // 最大解密字节数，超出最大字节数需要分组加密
        MAX_ENCRYPT_BLOCK = 128;
        // 标识
        offSet = 0;
        resultBytes = new byte[]{};
        cache = new byte[]{};
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }

        // String s1 = new String(cipher.doFinal(MzTranscodeUtils.base64StrToByteArray(s2)), StandardCharsets.UTF_8);
        log.info("{}",new String(resultBytes , StandardCharsets.UTF_8));
    }

    @SneakyThrows
    @Test
    public void test6(){
        String ivStr = "12345678";
        String aaa = MzCipherUtils.encryptByDES("{'user_id':12312312312}", "12345678", ivStr);
        System.out.println("aaa = " + aaa);
        System.out.println(MzCipherUtils.decryptByDES(aaa, "12345678", ivStr));
        String s = MzCipherUtils.decryptByDES("e43946caa497d1ea0ede0d8483c766ed4b1b95a8a57f6f544e503ea702a614958ca24b63187d2a84f69ebb2660e58b6e1b1efa82c76cc3d87326192b51c135d3c137ecd2fb2daa58eed20d92b589b82b3b6c9b5732b4b158", "12345678", ivStr);
        System.out.println("s = " + s);
    }



}
