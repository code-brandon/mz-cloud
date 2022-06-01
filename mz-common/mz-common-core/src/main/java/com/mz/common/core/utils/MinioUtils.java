package com.mz.common.core.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @Author: 小政同学 QQ:xiaozheng666888@qq.com
 * @Date: 2020/12/01/10:02
 * @Description: Minio工具类
 */
@Slf4j
public class MinioUtils {

    private static MinioClient minioClient;

    private static String endpoint;
    private static String bucketName;
    private static String accessKey;
    private static String secretKey;

    private static final String SEPARATOR = "/";

    private MinioUtils() {
    }

    public MinioUtils(String endpoint, String bucketName, String accessKey, String secretKey) {
        MinioUtils.endpoint = endpoint;
        MinioUtils.bucketName = bucketName;
        MinioUtils.accessKey = accessKey;
        MinioUtils.secretKey = secretKey;
        createMinioClient();
    }

    /**
     * 创建minioClient
     */
    public void createMinioClient() {
        try {
            if (null == minioClient) {
                log.info("minioClient 创建开始");
                minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey)
                        .build();
                createBucket();
                log.info("Minio----------------->> 初始化完成！！");
            }
        } catch (Exception e) {
            log.error("连接MinIO服务器异常：{}", e);
        }
    }

    /**
     * 获取上传文件的基础路径
     *
     * @return url
     */
    public static String getBasisUrl() {
        return endpoint + SEPARATOR + bucketName + SEPARATOR;
    }

    // TODO 操作存储桶

    /**
     * 初始化Bucket
     *
     * @throws Exception 异常
     */
    private static void createBucket()
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, RegionConflictException, io.minio.errors.ServerException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 验证bucketName是否存在
     *
     * @return boolean true:存在
     */
    public static boolean bucketExists()
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    public static void createBucket(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, RegionConflictException, io.minio.errors.ServerException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).build());
        }
    }

    /**
     * 获取存储桶策略
     *
     * @param bucketName 存储桶名称
     * @return json
     */
    private String getBucketPolicy(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, BucketPolicyTooLargeException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient
                .getBucketPolicy(GetBucketPolicyArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).build());
    }

    /**
     * 获取全部bucket
     * <p>
     * https://docs.minio.io/cn/java-client-api-reference.html#listBuckets
     */
    public static List<Bucket> getAllBuckets()
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    public static Optional<Bucket> getBucket(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    public static void removeBucket(String bucketName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.ServerException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).build());
    }

    // TODO 操作文件对象

    /**
     * 判断文件是否存在
     *
     * @param bucketName 存储桶
     * @param objectName 对象
     * @return true：存在
     */
    public static boolean doesObjectExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            minioClient
                    .statObject(StatObjectArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 判断文件夹是否存在
     *
     * @param bucketName 存储桶
     * @param objectName 文件夹名称（去掉/）
     * @return true：存在
     */
    public static boolean doesFolderExist(String bucketName, String objectName) {
        boolean exist = false;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).prefix(objectName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && objectName.equals(item.objectName())) {
                    exist = true;
                }
            }
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 根据文件前置查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @param recursive  是否递归查询
     * @return MinioItem 列表
     */
    public static List<Item> getAllObjectsByPrefix(String bucketName, String prefix,
                                                   boolean recursive)
            throws ErrorResponseException, InsufficientDataException, io.minio.errors.InternalException, InvalidBucketNameException, InvalidKeyException, InvalidResponseException,
            IOException, NoSuchAlgorithmException, XmlParserException, io.minio.errors.ServerException {
        List<Item> list = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
                ListObjectsArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).prefix(prefix).recursive(recursive).build());
        if (objectsIterator != null) {
            for (Result<Item> o : objectsIterator) {
                Item item = o.get();
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 获取文件流
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    public static InputStream getObject(String bucketName, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient
                .getObject(GetObjectArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).build());
    }

    /**
     * 断点下载
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param offset     起始字节的位置
     * @param length     要读取的长度
     * @return 流
     */
    public InputStream getObject(String bucketName, String objectName, long offset, long length)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient.getObject(
                GetObjectArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).offset(offset).length(length)
                        .build());
    }

    /**
     * 获取路径下文件列表
     *
     * @param bucketName bucket名称
     * @param prefix     文件名称
     * @param recursive  是否递归查找，如果是false,就模拟文件夹结构查找
     * @return 二进制流
     */
    public static Iterable<Result<Item>> listObjects(String bucketName, String prefix,
                                                     boolean recursive) {
        return minioClient.listObjects(
                ListObjectsArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).prefix(prefix).recursive(recursive).build());
    }

    /**
     * 通过MultipartFile，上传文件
     *
     * @param bucketName 存储桶
     * @param file       文件
     * @param objectName 对象名
     * @param contentType 对象类型
     */
    public static ObjectWriteResponse putObject(String bucketName, MultipartFile file,
                                                String objectName, String contentType)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        InputStream inputStream = file.getInputStream();
        return minioClient.putObject(
                PutObjectArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).contentType(contentType)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
    }

    /**
     * 上传本地文件
     *
     * @param bucketName 存储桶
     * @param objectName 对象名称
     * @param fileName   本地文件路径
     */
    public static ObjectWriteResponse putObject(String bucketName, String objectName,
                                                String fileName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).filename(fileName).build());
    }

    /**
     * 通过流上传文件
     *
     * @param bucketName  存储桶
     * @param objectName  文件对象
     * @param inputStream 文件流
     */
    public static ObjectWriteResponse putObject(String bucketName, String objectName,
                                                InputStream inputStream)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient.putObject(
                PutObjectArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).stream(
                        inputStream, inputStream.available(), -1)
                        .build());
    }

    /**
     * 创建文件夹或目录
     *
     * @param bucketName 存储桶
     * @param objectName 目录路径
     */
    public static ObjectWriteResponse putDirObject(String bucketName, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient.putObject(
                PutObjectArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).stream(
                        new ByteArrayInputStream(new byte[]{}), 0, -1)
                        .build());
    }

    /**
     * 获取文件信息, 如果抛出异常则说明文件不存在
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    public static ObjectStat statObject(String bucketName, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient
                .statObject(StatObjectArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).build());
    }

    /**
     * 拷贝文件
     *
     * @param bucketName    bucket名称
     * @param objectName    文件名称
     * @param srcBucketName 目标bucket名称
     * @param srcObjectName 目标文件名称
     */
    public static ObjectWriteResponse copyObject(String bucketName, String objectName,
                                                 String srcBucketName, String srcObjectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient.copyObject(
                CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).build())
                        .bucket(srcBucketName)
                        .object(srcObjectName)
                        .build());
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    public static void removeObject(String bucketName, String objectName)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, io.minio.errors.InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        minioClient
                .removeObject(RemoveObjectArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).object(objectName).build());
    }

    /**
     * 批量删除文件
     *
     * @param bucketName bucket
     * @param keys       需要删除的文件列表
     * @return
     */
    public static Iterable<Result<DeleteError>> removeObjectsAndReturnError(String bucketName, List<String> keys) {
        List<DeleteObject> objects = new LinkedList<>();
        keys.forEach(s -> {
            objects.add(new DeleteObject(s));
        });
        return minioClient.removeObjects(
                RemoveObjectsArgs.builder().bucket("".equals(bucketName) || Objects.isNull(bucketName) ? MinioUtils.bucketName : bucketName).objects(objects).build());
    }

    public static void removeObjects(String bucketName, List<String> keys) {
        List<DeleteObject> objects = new LinkedList<>();
        keys.forEach(s -> {
            objects.add(new DeleteObject(s));
            try {
                removeObject(bucketName, s);
            } catch (Exception e) {
                log.error("批量删除失败！error:{}", e);
            }
        });
    }

    // TODO 操作Presigned

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7 秒级
     * @return url
     */
    public static String getPresignedObjectUrl(String bucketName, String objectName,
                                               Integer expires)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, InvalidExpiresRangeException, io.minio.errors.InternalException, NoSuchAlgorithmException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        return minioClient.presignedGetObject(bucketName, objectName, expires);
    }

    /**
     * 给presigned URL设置策略
     *
     * @param bucketName 存储桶
     * @param objectName 对象名
     * @param expires    过期策略
     * @return map
     */
    public static Map<String, String> presignedGetObject(String bucketName, String objectName,
                                                         Integer expires)
            throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, InvalidExpiresRangeException, ServerException, InternalException, NoSuchAlgorithmException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException {
        PostPolicy policy = new PostPolicy(bucketName, objectName,
                ZonedDateTime.now().plusDays(7));
        policy.setContentType("image/png");
        return minioClient.presignedPostPolicy(policy);
    }


    /**
     * 将URLDecoder编码转成UTF8
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException {
        String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        return URLDecoder.decode(url, "UTF-8");
    }

}