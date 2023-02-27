package com.mz.common.mybatis.cache;


import java.util.concurrent.ConcurrentHashMap;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.mybatis.cache
 * @ClassName: MzMybatisCache
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/25 21:01
 */
public class MzMybatisCache {
    private static ConcurrentHashMap<String, MzMapperClass> mzMapperMap = new ConcurrentHashMap<>(5);

    private static final ThreadLocal<Boolean> mzIgnoreDataAuth = ThreadLocal.withInitial(()-> Boolean.FALSE);

    public static ConcurrentHashMap<String, MzMapperClass> getMzMapperMap() {
        return mzMapperMap;
    }

    public static MzMapperClass getMzMapperValue(String key) {
        return mzMapperMap.get(key);
    }

    public static Boolean getMzIgnoreDataAuth() {
        return mzIgnoreDataAuth.get();
    }

    public static void setMzIgnoreDataAuth(Boolean value) {
        mzIgnoreDataAuth.set(value);
    }

    public static void removeMzIgnoreDataAuth() {
        mzIgnoreDataAuth.remove();
    }
}
