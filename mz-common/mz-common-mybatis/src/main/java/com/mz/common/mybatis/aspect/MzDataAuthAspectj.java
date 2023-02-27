package com.mz.common.mybatis.aspect;

import com.mz.common.mybatis.annotation.MzIgnoreDataAuth;
import com.mz.common.mybatis.cache.MzMybatisCache;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * What -- Aop 实现数据权限 全局忽略
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.mybatis.aspect
 * @ClassName: MzDataAuthAspectj
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/26 12:28
 */
@Aspect
@Component
public class MzDataAuthAspectj {

    @SneakyThrows
    @Around("@annotation(mzIgnoreDataAuth)")
    public Object around(ProceedingJoinPoint point, MzIgnoreDataAuth mzIgnoreDataAuth) {
        MzMybatisCache.setMzIgnoreDataAuth(mzIgnoreDataAuth.value());
        try {
            return point.proceed();
        }finally {
            MzMybatisCache.removeMzIgnoreDataAuth();
        }
    }

}
