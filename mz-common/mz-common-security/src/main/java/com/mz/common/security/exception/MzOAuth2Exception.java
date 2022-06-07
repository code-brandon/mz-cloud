package com.mz.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * What -- OAuth 2 异常的基本异常 实现
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzOAuth2Exception
 * @CreateTime 2022/6/7 19:23
 */
@JsonSerialize(using = MzAuth2ExceptionSerializer.class)
public class MzOAuth2Exception extends OAuth2Exception {
    public MzOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public MzOAuth2Exception(String msg) {
        super(msg);
    }
}	
