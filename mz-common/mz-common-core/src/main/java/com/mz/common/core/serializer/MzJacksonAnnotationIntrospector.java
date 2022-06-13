package com.mz.common.core.serializer;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.mz.common.core.annotation.DictFormat;

/**
 * What -- 自定义项目内省器
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzJacksonAnnotationIntrospector
 * @CreateTime 2022/6/13 10:22
 */
public class MzJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {

    public MzJacksonAnnotationIntrospector() {}

    @Override
    public Object findSerializer(Annotated a) {
        // 如果是字典注解
        DictFormat dict = _findAnnotation(a, DictFormat.class);
        if(dict != null) {
            return new MzDictJsonSerializer(dict);
        }
        // 其他扩展。。。
        return super.findSerializer(a);
    }

}
