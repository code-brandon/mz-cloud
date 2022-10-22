package com.mz.common.core.context;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * What -- 默认上下文Holder
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.core.context
 * @ClassName: MzDefaultContextHolder
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/10/22 22:30
 */
@Data
public class MzDefaultContextHolder {

    public static final ThreadLocal<Map<String, Object>> CONTEXT_HOLDER =  new InheritableThreadLocal<Map<String, Object>>() {
        /**
         * 初始化 局部线程中的值
         */
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>(5);
        }
    };
}
