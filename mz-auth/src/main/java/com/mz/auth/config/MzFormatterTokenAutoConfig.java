package com.mz.auth.config;

import com.mz.auth.provider.MzFormatterToken;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * What -- 格式化器令牌自动配置
 * <br>
 * Describe -- {@like <a href="https://cloud.tencent.com/developer/article/1617606">文章地址</a>}
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzFormatterTokenAutoConfig
 * @CreateTime 2022/9/30 0:00
 */
@Configuration
public class MzFormatterTokenAutoConfig implements ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerAdapter handlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        List<HandlerMethodReturnValueHandler> returnValueHandlers = handlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>();
        newHandlers.add(new MzFormatterToken());
        assert returnValueHandlers != null;
        newHandlers.addAll(returnValueHandlers);
        handlerAdapter.setReturnValueHandlers(newHandlers);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}