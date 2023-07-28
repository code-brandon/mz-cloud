package com.mz.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * What -- i18n 消息源配置
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MessageSourceConfig
 * @CreateTime 2023/3/27 19:01
 */
public class MessageSourceConfig {

    @Bean("mzMessageSource")
    public MessageSourceAccessor mzMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.addBasenames( "org.springframework.security.messages","i18n/messages", "exc", "classpath:i18n/messages");
        messageSource.setDefaultLocale(Locale.CHINA);
        return new MessageSourceAccessor(messageSource);
    }

}