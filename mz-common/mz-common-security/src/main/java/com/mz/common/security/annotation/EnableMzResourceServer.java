package com.mz.common.security.annotation;

import com.mz.common.security.config.MzResourceServerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({ MzResourceServerConfig.class})
public @interface EnableMzResourceServer {

}