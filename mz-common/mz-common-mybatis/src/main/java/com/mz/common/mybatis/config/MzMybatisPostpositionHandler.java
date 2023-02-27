package com.mz.common.mybatis.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mz.common.mybatis.cache.MzMapperClass;
import com.mz.common.mybatis.cache.MzMapperMethod;
import com.mz.common.mybatis.cache.MzMybatisCache;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * What -- Mz Mybatis 后置处理器
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.mybatis.config
 * @ClassName: MzMybatisPostpositionHandler
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/25 18:54
 */
public class MzMybatisPostpositionHandler implements BeanPostProcessor {

    /**
     * Apply this {@code BeanPostProcessor} to the given new bean instance <i>after</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
     * instance and the objects created by the FactoryBean (as of Spring 2.0). The
     * post-processor can decide whether to apply to either the FactoryBean or created
     * objects or both through corresponding {@code bean instanceof FactoryBean} checks.
     * <p>This callback will also be invoked after a short-circuiting triggered by a
     * {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
     * in contrast to all other {@code BeanPostProcessor} callbacks.
     * <p>The default implementation returns the given {@code bean} as-is.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws BeansException in case of errors
     * @see InitializingBean#afterPropertiesSet
     * @see FactoryBean
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof SqlSessionFactory) {
            Configuration configuration = ((SqlSessionFactory) bean).getConfiguration();
            MapperRegistry mapperRegistry = configuration.getMapperRegistry();
            Collection<Class<?>> mappers = mapperRegistry.getMappers();
            for (Class<?> mapper : mappers) {
                MzMapperClass mzMapperClass = new MzMapperClass();
                mzMapperClass.setName(mapper.getName());
                mzMapperClass.setAClass(mapper);
                Map<String, List<MzMapperMethod>> mzMapperMethodMap = Maps.newHashMap();
                for (Method method : mapper.getMethods()) {
                    List<MzMapperMethod> mzMapperMethods = Lists.newArrayList();
                    String name = method.getName();
                    Class<?>[] types = method.getParameterTypes();
                    Class<?> returnType = method.getReturnType();
                    MzMapperMethod mzMapperMethod = new MzMapperMethod();
                    mzMapperMethod.setName(name);
                    mzMapperMethod.setTypes(types);
                    mzMapperMethod.setReturnType(returnType);
                    mzMapperMethod.setMethod(method);
                    if (mzMapperMethodMap.containsKey(name)) {
                        List<MzMapperMethod> methods = mzMapperMethodMap.get(name);
                        methods.add(mzMapperMethod);
                    }else {
                        mzMapperMethods.add(mzMapperMethod);
                        mzMapperMethodMap.put(name, mzMapperMethods);
                    }
                }
                mzMapperClass.setMethods(mzMapperMethodMap);
                MzMybatisCache.getMzMapperMap().put(mzMapperClass.getName(), mzMapperClass);
            }
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
