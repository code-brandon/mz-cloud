package com.mz.common.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mz.common.core.annotation.DictFormat;
import com.mz.common.core.utils.cach.DictCacheUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

public class MzDictJsonSerializer extends JsonSerializer<Object> {

    private DictFormat dictFormat;

    public MzDictJsonSerializer(DictFormat dict) {
        this.dictFormat = dict;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String key = dictFormat.dictKey();
        String type = dictFormat.dictType();
        String defaultValue = dictFormat.defaultValue();

        if (StringUtils.isBlank(type) || Objects.isNull(value)) {
            // 类型为空表示没走缓存 原值输出
            gen.writeObject(value);
        } else {
            String cacheValue = DictCacheUtils.getCache(type, StringUtils.isBlank(key) ? String.valueOf(value) : key);
            gen.writeObject(StringUtils.isNotBlank(cacheValue) ? cacheValue : StringUtils.isNotBlank(defaultValue) ? defaultValue : value);
        }
    }
}
