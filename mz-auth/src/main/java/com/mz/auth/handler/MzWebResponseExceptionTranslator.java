package com.mz.auth.handler;

import com.mz.common.core.entity.R;
import com.mz.common.core.exception.MzCodeEnum;
import com.netflix.client.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import javax.security.auth.message.AuthException;

/**
 * What -- Mz Web 响应异常翻译器
 * <br>
 * Describe -- 重写默认的实现自定义
 * <br>
 * {@link <a href="https://blog.csdn.net/Anenan/article/details/109737249">对异常翻译捕获</a>}
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzWebResponseExceptionTranslator
 * @CreateTime 2022/6/7 19:20
 */
@Slf4j
public class MzWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity translate(Exception e) {
        log.warn("登录失败: ", e.getLocalizedMessage());
        MzCodeEnum oauthAuthException = MzCodeEnum.UNKNOW_EXCEPTION;
        if (e instanceof AuthException || e.getCause() instanceof AuthException) {
            oauthAuthException = MzCodeEnum.OAUTH_EXCEPTION;
        } else if (e instanceof InternalAuthenticationServiceException) {
            if (e.getCause().getCause() instanceof ClientException) {
                oauthAuthException = MzCodeEnum.OAUTH_CLIENT_EXCEPTION;
            } else {
                oauthAuthException = MzCodeEnum.OAUTH_AUTH_EXCEPTION;
            }
        } else if (e instanceof InvalidGrantException) {
            oauthAuthException = MzCodeEnum.LOGINACCT_PASSWORD_INVAILD_EXCEPTION;
        } else if (e instanceof InvalidTokenException) {
            oauthAuthException = MzCodeEnum.OAUTH_TOKEN_EXCEPTION;
        } else if (e instanceof UnsupportedGrantTypeException) {
            oauthAuthException = MzCodeEnum.OAUTH_GRANTTYPE_EXCEPTION;
        }
        return ResponseEntity.ok(R.fail(oauthAuthException));
    }

}
