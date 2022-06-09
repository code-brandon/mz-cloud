package com.mz.common.mybatis.plugin;

import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * What -- 解决Mps非驼峰映射
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzObjectWrapperFactoryConverter
 * @CreateTime 2022/6/9 18:16
 */
public class MzObjectWrapperFactoryConverter implements Converter<String, ObjectWrapperFactory> {
    @Override
    public ObjectWrapperFactory convert(String source) {
        try {
            return (ObjectWrapperFactory) Class.forName(source).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}