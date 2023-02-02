package com.mz.common.utils;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.utils
 * @ClassName: IPSearcherUtils
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/1/9 21:57
 */
public class IPSearcherUtils {

    private static final Logger logger = LoggerFactory.getLogger(IPSearcherUtils.class);

    private static byte[] bytes = null;
    private void init(){
        // 1、创建 searcher 对象

        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;

        try {
            inputStream = new ClassPathResource("ip2region.xdb").getURL().openStream();
            baos = new ByteArrayOutputStream();
            FileCopyUtils.copy(inputStream, baos);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Objects.requireNonNull(baos).close();
                Objects.requireNonNull(inputStream).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * 获取IP解析单例
     *
     * @return Ip2regionAnalysis
     */
    private  Searcher getSearcher() {
        if (bytes == null) {
            synchronized (IPSearcherUtils.class) {
                if (bytes == null) {
                   init();
                }
            }
        }
        try {
            return Searcher.newWithBuffer(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String searcher(String ip) {
        if (StringUtils.isBlank(ip)) {
            return "";
        }
        Searcher searcher = null;
        IPSearcherUtils ipSearcherUtils = new IPSearcherUtils();
        // 2、查询
        try {
            searcher = ipSearcherUtils.getSearcher();
            return searcher.search(ip);
        } catch (Exception e) {
            logger.error("failed to search({}): {}", ip, e.getMessage());
        }finally {
            // 3、关闭资源
            try {
                if (searcher != null) {
                    searcher.close();
                }
            } catch (IOException e) {
                logger.error("failed to close search({}): {}", ip, e.getMessage());
            }
        }
        return "";
    }


}
