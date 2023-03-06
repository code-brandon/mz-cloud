package com.mz.common.mybatis.cache;

import cn.hutool.core.util.ReUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.binding.MapperMethod;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor

@Data
public class MzMapperClass {
    /**
     * 类名
     */
    private String name;

    /**
     * 当前类的Class
     */
    private Class<?> aClass;

    /**
     * 当前类的方法
     */

    private Map<String, List<MzMapperMethod>> methods;


    public List<MzMapperMethod> getMethodValues(String key) {
        return methods.get(key);
    }

    public MzMapperMethod getMethod(String key,Object parameter) {
        MzMapperMethod mzMapperMethod = null;
        List<MzMapperMethod> methodValues = getMethodValues(key);
        if (methodValues.size() == 1) {
            return methodValues.get(0);
        }
        for (MzMapperMethod methodValue : methodValues) {
            if (parameter instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameter;
                long sum = paramMap.keySet().stream().filter(f -> ReUtil.isMatch("^(param)[0-9]+", (String) f)).count();
                Class<?>[] types = methodValue.getTypes();
                int count = 0;
                for (Class< ? > type: types) {
                    for (int i = 1; i <= sum; i++) {
                        Object value = ((MapperMethod.ParamMap<?>) parameter).get("param" + i);
                        if (type.isAssignableFrom(value.getClass()) || value.getClass().isAssignableFrom(type)) {
                            count++;
                        }
                    }
                }
                if (count == types.length) {
                    mzMapperMethod = methodValue;
                }
            }else {
                for (Class<?> type : methodValue.getTypes()) {
                    if (type.isAssignableFrom(parameter.getClass()) || parameter.getClass().isAssignableFrom(type)) {
                        mzMapperMethod = methodValue;
                    }
                }

            }
        }
        return mzMapperMethod;
    }


}