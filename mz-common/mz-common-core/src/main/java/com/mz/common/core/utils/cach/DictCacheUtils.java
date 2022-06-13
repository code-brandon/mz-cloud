package com.mz.common.core.utils.cach;

import com.mz.common.core.entity.dict.DictData;
import com.mz.common.core.entity.dict.DictEntity;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * What -- 字典缓存工具类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: DictCacheUtils
 * @CreateTime 2022/6/13 10:32
 */
@UtilityClass
@Slf4j
public class DictCacheUtils {

    private  ConcurrentHashMap<String, DictEntity> cache = new ConcurrentHashMap<>(256);

    public  ConcurrentHashMap<String, DictEntity> getAllCache() {
        return cache;
    }

    public  void putAllCache(Map<String, DictEntity> cache) {
        DictCacheUtils.cache.putAll(cache);
    }

    public  void putCache(String k, DictEntity v) {
        cache.put(k, v);
    }

    public  void removeCache(String k) {
        cache.remove(k);
    }

    public  DictEntity getCache(String k) {
        return cache.get(k);
    }

    public  String getCache(String k,String dv) {
        DictEntity dictEntity = cache.get(k);
        if (Objects.isNull(dictEntity)) {
            return null;
        }

        if (!k.equals(dictEntity.getDictType())) {
            log.warn("DictFormat 中 DictType 与 缓存中的K不一致！！");
        }

        List<DictData> dictData = dictEntity.getDictDatas().stream().filter(d -> dv.equals(d.getDictValue())).collect(Collectors.toList());
        if (dictData == null || dictData.size() == 0) {
            return null;
        }
        if (dictData.size() > 1) {
            log.warn("缓存字典类型中存在两个相同的dv");
            return null;
        }

        DictData data = dictData.get(0);
        return data.getDictLabel();
    }

    public  Integer getCount() {
        return cache.size();
    }

}
