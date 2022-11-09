package com.xiaozheng.encrypt.utils;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.*;

/**
 * What -- 密码工具类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: CipherUtils
 * @CreateTime 2022/11/2 22:58
 */
public class MzCipherUtils {

    /**
     * MD5算法
     */
    private static final String ALGORITHM_MD5 = "MD5";
    /**
     * SHA算法
     */
    private static final String ALGORITHM_SHA = "SHA";
    /**
     * HMAC算法
     */
    private static final String ALGORITHM_MAC = "HmacMD5";
    /**
     * DES算法
     */
    private static final String ALGORITHM_DES = "DES";
    /**
     * PBE算法
     */
    private static final String ALGORITHM_PBE = "PBEWITHMD5andDES";

    /**
     * AESkey
     */
    private static final String KEY_AES = "AES";

    /**
     * AES算法
     */
    private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";

    public static final String ALGORITHM_ECB_AES = "AES/ECB/PKCS5Padding";

    public static final String ALGORITHM_CBC_AES = "AES/CBC/PKCS5Padding";

    public static final String ALGORITHM_ECB_RSA = "RSA/ECB/PKCS1Padding";

    /**
     * RSA算法
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 数字签名
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 公钥
     */
    public static final String RSAPUBLIC_KEY = "RSAPublicKey";

    /**
     * 私钥
     */
    public static final String RSAPRIVATE_KEY = "RSAPrivateKey";

    /**
     * D-H算法
     */
    public static final String ALGORITHM_DH = "DH";

    /**
     * 默认密钥字节数
     *
     * <pre>
     * DH
     * Default Keysize 1024
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     * </pre>
     */
    private static final int DH_KEY_SIZE = 1024;

    private static final int RSA_KEY_SIZE = 2048;

    /**
     * DH加密下需要一种对称加密算法对数据加密，这里我们使用DES，也可以使用其他对称加密算法。
     */
    private static final String SECRET_ALGORITHM = "DES";

    /**
     * DH公钥
     */
    private static final String DHPUBLIC_KEY = "DHPublicKey";

    /**
     * DH私钥
     */
    private static final String DHPRIVATE_KEY = "DHPrivateKey";

    /**
     * Java密钥库(Java Key Store，JKS)KEY_STORE
     */
    private static final String KEY_STORE = "JKS";

    private static final String X509 = "X.509";

    /**
     * 信息摘要算法
     *
     * @param algorithm 算法类型
     * @param data      要加密的字符串
     * @return 返回加密后的摘要信息
     */
    private static String encryptEncode(String algorithm, String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return MzTranscodeUtils.byteArrayToHexStr(md.digest(data.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 使用MD5加密
     *
     * @param data 要加密的字符串
     * @return 返回加密后的信息
     */
    public static String MD5Encode(String data) {
        return encryptEncode(ALGORITHM_MD5, data);
    }

    /**
     * 使用SHA加密
     *
     * @param data 要加密的字符串
     * @return 返回加密后的信息
     */
    public static String SHAEncode(String data) {
        return encryptEncode(ALGORITHM_SHA, data);
    }

    /**
     * 生成HMAC密钥
     *
     * @return 返回密钥信息
     */
    public static String generateMACKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_MAC);
            SecretKey secretKey = keyGenerator.generateKey();
            return MzTranscodeUtils.byteArrayToBase64Str(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用HMAC加密
     *
     * @param data 要加密的字符串
     * @param key  密钥
     * @return 返回加密后的信息
     */
    public static String HMACEncode(String data, String key) {
        Key k = toKey(key, ALGORITHM_MAC);
        try {
            Mac mac = Mac.getInstance(k.getAlgorithm());
            mac.init(k);
            return MzTranscodeUtils.byteArrayToBase64Str(mac.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将base64编码后的密钥字符串转换成密钥对象
     *
     * @param key       密钥字符串
     * @param algorithm 加密算法
     * @return 返回密钥对象
     */
    private static Key toKey(String key, String algorithm) {
        SecretKey secretKey = new SecretKeySpec(MzTranscodeUtils.base64StrToByteArray(key), algorithm);
        return secretKey;
    }

    /**
     * 生成DES密钥
     *
     * @param seed 密钥种子
     * @return 返回base64编码的密钥字符串
     */
    public static String generateDESKey(String seed) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_DES);
            kg.init(new SecureRandom(seed.getBytes()));
            SecretKey secretKey = kg.generateKey();
            return MzTranscodeUtils.byteArrayToBase64Str(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES加密
     *
     * @param data 要加密的数据
     * @param key  密钥
     * @return 返回加密后的数据(经过base64编码)
     */
    public static String DESEncrypt(String data, String key) {
        return DESCipher(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * DES解密
     *
     * @param data 要解密的数据
     * @param key  密钥
     * @return 返回解密后的数据
     */
    public static String DESDecrypt(String data, String key) {
        return DESCipher(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * DES的加密解密
     *
     * @param data 要加密或解密的数据
     * @param key  密钥
     * @param mode 加密或解密模式
     * @return 返回加密或解密的数据
     */
    private static String DESCipher(String data, String key, int mode) {
        try {
            Key k = toKey(key, ALGORITHM_DES);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(mode, k);
            return mode == Cipher.DECRYPT_MODE ? new String(cipher.doFinal(MzTranscodeUtils.base64StrToByteArray(data))) : MzTranscodeUtils.byteArrayToBase64Str(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成盐
     *
     * @return 返回base64编码后的盐信息
     */
    public static String generatePBESalt() {
        byte[] salt = new byte[8];
        Random random = new Random();
        random.nextBytes(salt);
        return MzTranscodeUtils.byteArrayToBase64Str(salt);
    }

    /**
     * PBE(Password-based encryption基于密码加密)加密
     *
     * @param data     要加密的数据
     * @param password 密码
     * @param salt     盐
     * @return 返回加密后的数据(经过base64编码)
     */
    public static String PBEEncrypt(String data, String password, String salt) {
        return PBECipher(data, password, salt, Cipher.ENCRYPT_MODE);
    }

    /**
     * PBE(Password-based encryption基于密码加密)解密
     *
     * @param data     要解密的数据
     * @param password 密码
     * @param salt     盐
     * @return 返回解密后的数据
     */
    public static String PBEDecrypt(String data, String password, String salt) {
        return PBECipher(data, password, salt, Cipher.DECRYPT_MODE);
    }

    /**
     * PBE加密解密
     *
     * @param data     要加密解密的信息
     * @param password 密码
     * @param salt     盐
     * @param mode     加密或解密模式
     * @return 返回加密解密后的数据
     */
    private static String PBECipher(String data, String password, String salt, int mode) {
        try {
            Key secretKey = toPBEKey(password);
            PBEParameterSpec paramSpec = new PBEParameterSpec(MzTranscodeUtils.base64StrToByteArray(salt), 100);
            Cipher cipher = Cipher.getInstance(ALGORITHM_PBE);
            cipher.init(mode, secretKey, paramSpec);
            return mode == Cipher.DECRYPT_MODE ? new String(cipher.doFinal(MzTranscodeUtils.base64StrToByteArray(data))) : MzTranscodeUtils.byteArrayToBase64Str(cipher.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成PBEkey
     *
     * @param password 使用的密码
     * @return 返回生成的PBEkey
     */
    private static Key toPBEKey(String password) {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM_PBE);
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            return secretKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成AESkey
     *
     * @param keySize key的位数
     * @param seed    随机种子
     * @return 返回base64编码后的key信息
     */
    public static String generateAESKey(int keySize, String seed) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            kgen.init(keySize, new SecureRandom(seed.getBytes()));
            SecretKey key = kgen.generateKey();
            return MzTranscodeUtils.byteArrayToBase64Str(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密
     *
     * @param data               要加密的数据
     * @param key                密钥
     * @param algorithmParameter 算法参数
     * @return 返回加密数据
     */
    public static String AESEncrypt(String data, String key, String algorithmParameter) {
        return AESCipher(data, key, algorithmParameter, Cipher.ENCRYPT_MODE);
    }

    /**
     * AES解密
     *
     * @param data               要解密的数据
     * @param key                密钥
     * @param algorithmParameter 算法参数
     * @return 返回解密数据
     */
    public static String AESDecrypt(String data, String key, String algorithmParameter) {
        return AESCipher(data, key, algorithmParameter, Cipher.DECRYPT_MODE);
    }

    /**
     * 实现AES加密解密
     *
     * @param data               要加密或解密的数据
     * @param key                密钥
     * @param algorithmParameter 算法参数
     * @param mode               加密或解密
     * @return 返回加密或解密的数据
     */
    private static String AESCipher(String data, String key, String algorithmParameter, int mode) {
        try {
            Key k = toKey(key, KEY_AES);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(algorithmParameter.getBytes());
            Cipher ecipher = Cipher.getInstance(ALGORITHM_AES);
            ecipher.init(mode, k, paramSpec);
            return mode == Cipher.DECRYPT_MODE ? new String(ecipher.doFinal(MzTranscodeUtils.base64StrToByteArray(data))) : MzTranscodeUtils.byteArrayToBase64Str(ecipher.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数字签名
     *
     * @param data       要签名的密文
     * @param privateKey 私钥
     * @return 返回签名信息
     */
    public static String RSASign(String data, String privateKey) {
        try {
            // 解密由base64编码的私钥
            byte[] keyBytes = MzTranscodeUtils.base64StrToByteArray(privateKey);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 取私钥匙对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(MzTranscodeUtils.base64StrToByteArray(data));
            return MzTranscodeUtils.byteArrayToBase64Str(signature.sign());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证签名
     *
     * @param data      要验证的密文
     * @param publicKey 公钥
     * @param sign      签名信息
     * @return 返回验证成功状态
     */
    public static boolean RSAVerify(String data, String publicKey, String sign) {
        try {
            // 解密由base64编码的公钥
            byte[] keyBytes = MzTranscodeUtils.base64StrToByteArray(publicKey);
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            // KEY_ALGORITHM 指定的加密算法
            Signature signature;
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 取公钥匙对象
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(MzTranscodeUtils.base64StrToByteArray(data));
            // 验证签名是否正常
            return signature.verify(MzTranscodeUtils.base64StrToByteArray(sign));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 私钥解密
     *
     * @param data 要解密的字符串
     * @param key  私钥
     * @return 返回解密后的字符串
     */
    public static String RSADecryptByPrivateKey(String data, String key) {
        try {
            // 对密钥解密
            byte[] keyBytes = MzTranscodeUtils.base64StrToByteArray(key);
            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(MzTranscodeUtils.base64StrToByteArray(data)));
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密
     *
     * @param data 要解密的数据
     * @param key  公钥
     * @return 返回解密后的数据
     */
    public static String RSADecryptByPublicKey(String data, String key) {
        try {
            // 对密钥解密
            byte[] keyBytes = MzTranscodeUtils.base64StrToByteArray(key);
            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(cipher.doFinal(MzTranscodeUtils.base64StrToByteArray(data)));
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param data 要加密的数据
     * @param key  公钥
     * @return 返回加密的数据
     */
    public static String RSAEncryptByPublicKey(String data, String key) {
        try {
            // 对公钥解密
            byte[] keyBytes = MzTranscodeUtils.base64StrToByteArray(key);
            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return MzTranscodeUtils.byteArrayToBase64Str(cipher.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param data 要加密的数据
     * @param key  私钥
     * @return 返回加密后的数据
     */
    public static String RSAEncryptByPrivateKey(String data, String key) {
        try {
            // 对密钥解密
            byte[] keyBytes = MzTranscodeUtils.base64StrToByteArray(key);
            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return MzTranscodeUtils.byteArrayToBase64Str(cipher.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得私钥
     *
     * @param keyMap 密钥对
     * @return 返回经过base64编码的私钥
     */
    public static String getRSAPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(RSAPRIVATE_KEY);
        return MzTranscodeUtils.byteArrayToBase64Str(key.getEncoded());
    }

    /**
     * 获得公钥(base64编码)
     *
     * @param keyMap 密钥对
     * @return 返回经过base64编码的公钥
     */
    public static String getRSAPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(RSAPUBLIC_KEY);
        return MzTranscodeUtils.byteArrayToBase64Str(key.getEncoded());
    }


    /**
     * 初始化密钥对
     *
     * @return 返回密钥对
     */
    public static Map<String, Object> initRSAKey() {
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(RSA_KEY_SIZE);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            keyMap.put(RSAPUBLIC_KEY, publicKey);
            keyMap.put(RSAPRIVATE_KEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyMap;
    }


    /**
     * 初始化密钥对
     *
     * @param keyStorePath keystore文件路径
     * @param alias        别名
     * @param password     密码
     * @return 返回密钥对
     */
    public static Map<String, Object> initRSAKey(String keyStorePath, String alias, String password) {
        Map<String, Object> keyMap = new HashMap<>(2);
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(RSA_KEY_SIZE);
            KeyPair keyPair = getKeyPair(keyStorePath, alias, password);
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            keyMap.put(RSAPUBLIC_KEY, publicKey);
            keyMap.put(RSAPRIVATE_KEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyMap;
    }

    /**
     * 初始化甲方密钥对
     *
     * @return 返回甲方密钥对
     */
    public static Map<String, Object> initDHKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_DH);
            keyPairGenerator.initialize(DH_KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 甲方公钥
            DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
            // 甲方私钥
            DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap<String, Object>(2);
            keyMap.put(DHPUBLIC_KEY, publicKey);
            keyMap.put(DHPRIVATE_KEY, privateKey);
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    /**
     * 使用甲方公钥初始化乙方密钥对
     *
     * @param key 甲方公钥
     * @return 返回乙方密钥对
     */
    public static Map<String, Object> initDHKey(String key) {
        try {
            // 解析甲方公钥
            byte[] keyBytes = MzTranscodeUtils.base64StrToByteArray(key);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_DH);
            PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
            // 由甲方公钥构建乙方密钥
            DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
            keyPairGenerator.initialize(dhParamSpec);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 乙方公钥
            DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
            // 乙方私钥
            DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap<>(2);
            keyMap.put(DHPUBLIC_KEY, publicKey);
            keyMap.put(DHPRIVATE_KEY, privateKey);
            return keyMap;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    /**
     * DH加密
     *
     * @param data       要加密的数据
     * @param publicKey  甲方或乙方公钥
     * @param privateKey 甲方或乙方私钥
     * @return 加密结果
     */
    public static String DHEncrypt(String data, String publicKey, String privateKey) {
        try {
            // 生成本地密钥
            SecretKey secretKey = getDHSecretKey(publicKey, privateKey);
            // 数据加密
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return MzTranscodeUtils.byteArrayToBase64Str(cipher.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DH解密
     *
     * @param data       要解密的数据
     * @param publicKey  公钥
     * @param privateKey 私钥
     * @return 返回解密结果
     */
    public static String DHDecrypt(String data, String publicKey, String privateKey) {
        try {
            // 生成本地密钥
            SecretKey secretKey = getDHSecretKey(publicKey, privateKey);
            // 数据解密
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(MzTranscodeUtils.base64StrToByteArray(data)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成本地密钥
     *
     * @param publicKey  公钥
     * @param privateKey 私钥
     * @return 返回本地密钥
     */
    private static SecretKey getDHSecretKey(String publicKey, String privateKey) {
        try {
            // 初始化公钥
            byte[] pubKeyBytes = MzTranscodeUtils.base64StrToByteArray(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_DH);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
            PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
            // 初始化私钥
            byte[] priKeyBytes = MzTranscodeUtils.base64StrToByteArray(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
            Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);
            KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
            keyAgree.init(priKey);
            keyAgree.doPhase(pubKey, true);
            // 生成本地密钥
            SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM);
            return secretKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取私钥
     *
     * @param keyMap 密钥对
     * @return 返回base64编码的私钥
     */
    public static String getDHPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(DHPRIVATE_KEY);
        return MzTranscodeUtils.byteArrayToBase64Str(key.getEncoded());
    }


    /**
     * 得到公钥
     *
     * @param publicKey
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getStringPublicKey(String publicKey) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance( KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 得到私钥
     *
     * @param privateKey
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getStringPrivateKey(String privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance( KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param keyMap 密钥对
     * @return 返回base64编码的公钥
     */
    public static String getDHPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(DHPUBLIC_KEY);
        return MzTranscodeUtils.byteArrayToBase64Str(key.getEncoded());
    }

    /**
     * 获取私钥
     *
     * @param keyStorePath keystore文件路径
     * @param alias        别名
     * @param password     密码
     * @return 返回私钥
     */
    public static PrivateKey getKeyStorePrivateKey(String keyStorePath, String alias, String password) {
        try {
            KeyStore ks = getStore(keyStorePath, password);
            PrivateKey key = (PrivateKey) ks.getKey(alias, password.toCharArray());
            return key;
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取密钥库
     * @param keyStorePath keystore文件路径
     * @param password  密码
     * @return
     */
    public static KeyStore getStore(String keyStorePath, String password) {
        return getKeyStore(keyStorePath, password);
    }

    /**
     * 获取密钥对
     * @param keyStorePath keystore文件路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    public static KeyPair getKeyPair(String keyStorePath,String alias, String password) {
        try {
            RSAPrivateCrtKey key = (RSAPrivateCrtKey) getStore(keyStorePath, password).getKey(alias, password.toCharArray());
            RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
            PublicKey publicKey = KeyFactory.getInstance( KEY_ALGORITHM).generatePublic(spec);
            return new KeyPair(publicKey, key);
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot load keys from store: ", e);
        }
    }

    /**
     * 获取公钥
     *
     * @param certificatePath 证书文件路径
     * @return 返回公钥
     */
    public static PublicKey getCertificatePublicKey(String certificatePath) {
        try {
            Certificate certificate = getCertificate(certificatePath);
            PublicKey key = certificate.getPublicKey();
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载证书文件
     *
     * @param certificatePath 证书文件路径
     * @return 返回证书
     */
    private static Certificate getCertificate(String certificatePath) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
            FileInputStream in = new FileInputStream(certificatePath);
            Certificate certificate = certificateFactory.generateCertificate(in);
            in.close();
            return certificate;
        } catch (CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取证书
     *
     * @param keyStorePath keystore文件路径
     * @param alias        别名
     * @param password     密码
     * @return 返回证书
     */
    private static Certificate getCertificate(String keyStorePath, String alias, String password) {
        try {
            KeyStore ks = getStore(keyStorePath, password);
            Certificate certificate = ks.getCertificate(alias);
            return certificate;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载KeyStore文件
     *
     * @param keyStorePath keystore文件地址
     * @param password     keystore密码
     * @return 返回KeyStore
     */
    public static KeyStore getKeyStore(String keyStorePath, String password) {
        try(FileInputStream is = new FileInputStream(keyStorePath)) {
            KeyStore ks = KeyStore.getInstance(KEY_STORE);
            ks.load(is, password.toCharArray());
            return ks;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密数据
     *
     * @param data         要加密的数据
     * @param keyStorePath keystore路径
     * @param alias        别名
     * @param password     密码
     * @return 返回加密后的数据
     */
    public static String encryptByPrivateKey(String data, String keyStorePath,
                                             String alias, String password) {
        try {
            // 取得私钥
            PrivateKey privateKey = getKeyStorePrivateKey(keyStorePath, alias, password);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return MzTranscodeUtils.byteArrayToBase64Str(cipher.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param data         要解密的数据
     * @param keyStorePath keystore路径
     * @param alias        别名
     * @param password     密码
     * @return 返回解密后的数据
     */
    public static String decryptByPrivateKey(String data, String keyStorePath, String alias, String password) {
        try {
            // 取得私钥
            PrivateKey privateKey = getKeyStorePrivateKey(keyStorePath, alias, password);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(MzTranscodeUtils.base64StrToByteArray(data)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param data            要加密的数据
     * @param certificatePath 证书路径
     * @return 返回加密后的信息
     */
    public static String encryptByPublicKey(String data, String certificatePath) {
        try {
            // 取得公钥
            PublicKey publicKey = getCertificatePublicKey(certificatePath);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return MzTranscodeUtils.byteArrayToBase64Str(cipher.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密
     *
     * @param data            要解密的数据
     * @param certificatePath 证书路径
     * @return 返回解密信息
     */
    public static String decryptByPublicKey(String data, String certificatePath) {
        try {
            // 取得公钥
            PublicKey publicKey = getCertificatePublicKey(certificatePath);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(cipher.doFinal(MzTranscodeUtils.base64StrToByteArray(data)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * RSA公钥加密  (超长加密)
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String publicKeyEncrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        //当长度过长的时候，需要分割后加密 117个字节
        byte[] resultBytes = getMaxResultEncrypt(str, cipher);

        return Base64.encodeBase64String(resultBytes);
    }

    private static byte[] getMaxResultEncrypt(String str, Cipher cipher) {
        byte[] inputArray = str.getBytes();
        // 最大加密字节数，超出最大字节数需要分组加密
        int MAX_ENCRYPT_BLOCK = 117;
        return getRSABytes(cipher, inputArray, MAX_ENCRYPT_BLOCK);
    }

    /**
     * RSA私钥解密  (超长解密)
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String privateKeyDecrypt(String str, String privateKey) throws Exception {
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        //当长度过长的时候，需要分割后解密 128个字节
        return new String(getMaxResultDecrypt(str,cipher),StandardCharsets.UTF_8);
    }

    private static byte[] getMaxResultDecrypt(String str, Cipher cipher){
        byte[] inputArray = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
        // 最大解密字节数，超出最大字节数需要分组加密
        int MAX_ENCRYPT_BLOCK = 128;
        return getRSABytes(cipher, inputArray, MAX_ENCRYPT_BLOCK);
    }


    /**
     *  RSA 分段加密
     * @param cipher
     * @param inputArray
     * @param MAX_ENCRYPT_BLOCK
     * @return
     */
    @SneakyThrows
    public static byte[] getRSABytes(Cipher cipher, byte[] inputArray, int MAX_ENCRYPT_BLOCK){
        int inputLength = inputArray.length;
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
        return resultBytes;
    }

    /**
     * 验证证书是否过期
     *
     * @param certificatePath 证书路径
     * @return 返回验证结果
     */
    public static boolean verifyCertificate(String certificatePath) {
        return verifyCertificate(new Date(), certificatePath);
    }

    /**
     * 验证证书是否过期
     *
     * @param date            日期
     * @param certificatePath 证书路径
     * @return 返回验证结果
     */
    public static boolean verifyCertificate(Date date, String certificatePath) {
        boolean status = true;
        try {
            // 取得证书
            Certificate certificate = getCertificate(certificatePath);
            // 验证证书是否过期或无效
            status = verifyCertificate(date, certificate);
        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    /**
     * 验证证书是否过期
     *
     * @param date        日期
     * @param certificate 证书
     * @return 返回验证结果
     */
    private static boolean verifyCertificate(Date date, Certificate certificate) {
        boolean status = true;
        try {
            X509Certificate x509Certificate = (X509Certificate) certificate;
            x509Certificate.checkValidity(date);
        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    /**
     * 对于数据进行签名
     *
     * @param sign         要签名的信息
     * @param keyStorePath keystore文件位置
     * @param alias        别名
     * @param password     密码
     * @return 返回签名信息
     */
    public static String sign(String sign, String keyStorePath, String alias, String password) {
        try {
            // 获得证书
            X509Certificate x509Certificate = (X509Certificate) getCertificate(
                    keyStorePath, alias, password);
            // 获取私钥
            KeyStore ks = getStore(keyStorePath, password);
            // 取得私钥
            PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password
                    .toCharArray());
            // 构建签名
            Signature signature = Signature.getInstance(x509Certificate
                    .getSigAlgName());
            signature.initSign(privateKey);
            signature.update(MzTranscodeUtils.base64StrToByteArray(sign));
            return MzTranscodeUtils.byteArrayToBase64Str(signature.sign());
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证签名信息
     *
     * @param data            要验证的信息
     * @param sign            签名信息
     * @param certificatePath 证书路径
     * @return 返回验证结果
     */
    public static boolean verify(String data, String sign, String certificatePath) {
        try {
            // 获得证书
            X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
            // 获得公钥
            PublicKey publicKey = x509Certificate.getPublicKey();
            // 构建签名
            Signature signature = Signature.getInstance(x509Certificate
                    .getSigAlgName());
            signature.initVerify(publicKey);
            signature.update(MzTranscodeUtils.base64StrToByteArray(data));
            return signature.verify(MzTranscodeUtils.base64StrToByteArray(sign));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证证书
     *
     * @param date         日期
     * @param keyStorePath keystore文件路径
     * @param alias        别名
     * @param password     密码
     * @return 返回验证结果
     */
    public static boolean verifyCertificate(Date date, String keyStorePath,
                                            String alias, String password) {
        boolean status = true;
        try {
            Certificate certificate = getCertificate(keyStorePath, alias,
                    password);
            status = verifyCertificate(date, certificate);
        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    /**
     * 验证证书
     *
     * @param keyStorePath keystore文件路径
     * @param alias        别名
     * @param password     密码
     * @return 返回验证结果
     */
    public static boolean verifyCertificate(String keyStorePath, String alias,
                                            String password) {
        return verifyCertificate(new Date(), keyStorePath, alias, password);
    }

}