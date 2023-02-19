package com.mz.common.core.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import lombok.extern.slf4j.Slf4j;

import java.time.chrono.ChronoLocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * What -- Mz 自定义 Bean 序列化器修饰符
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzCustomBeanSerializerModifier
 * @CreateTime 2022/6/10 17:55
 */
@Slf4j
public class MzCustomBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {

        for (BeanPropertyWriter beanProperty : beanProperties) {
            if (isArrayType(beanProperty)) {
                beanProperty.assignNullSerializer(new MzCustomNullJsonSerializers.NullArrayJsonSerializer());
            } else if (isStringType(beanProperty)) {
                beanProperty.assignNullSerializer(new MzCustomNullJsonSerializers.NullStringJsonSerializer());
            } else if (isNumberType(beanProperty)) {
                beanProperty.assignNullSerializer(new MzCustomNullJsonSerializers.NullNumberJsonSerializer());
            } else if (isBooleanType(beanProperty)) {
                beanProperty.assignNullSerializer(new MzCustomNullJsonSerializers.NullBooleanJsonSerializer());
            } else {
                beanProperty.assignNullSerializer(new MzCustomNullJsonSerializers.NullObjectJsonSerializer());
            }
        }
        return beanProperties;
    }

    /**
     * 是否是数组
     */
    private boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是String
     */
    private boolean isStringType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || ChronoLocalDateTime.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是数值类型
     */
    private boolean isNumberType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return Number.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是boolean
     */
    private boolean isBooleanType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(Boolean.class);
    }
}
