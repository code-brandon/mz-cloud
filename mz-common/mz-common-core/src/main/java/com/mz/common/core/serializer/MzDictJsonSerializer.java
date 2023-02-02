package com.mz.common.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mz.common.core.annotation.DictFormat;
import com.mz.common.core.dict.DictCache;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * What -- Mz 字典 Json序列化器
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzDictJsonSerializer
 * @CreateTime 2022/10/28 22:33
 */
public class MzDictJsonSerializer extends JsonSerializer<Object> {

    private final DictFormat dictFormat;

    public MzDictJsonSerializer(DictFormat dict) {
        this.dictFormat = dict;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String key = dictFormat.dictKey();
        String type = dictFormat.dictType();
        String newField = dictFormat.newField();
        String defaultValue = dictFormat.defaultValue();

        if (StringUtils.isBlank(type) || Objects.isNull(value)) {
            // 类型为空表示没走缓存 原值输出
            gen.writeObject(value);
        } else {
            String cacheValue = DictCache.getCache(type, StringUtils.isBlank(key) ? String.valueOf(value) : key);
            if (StringUtils.isBlank(newField)) {
                gen.writeObject(StringUtils.isNotBlank(cacheValue) ? cacheValue : StringUtils.isNotBlank(defaultValue) ? defaultValue : value);
            } else {
                gen.writeObject(value);
                gen.writeFieldName(newField);
                gen.writeObject(cacheValue);
            }
        }
    }
}
