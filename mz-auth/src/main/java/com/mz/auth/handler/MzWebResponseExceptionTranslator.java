package com.mz.auth.handler;

import com.mz.common.constant.enums.MzErrorCodeEnum;
import com.mz.common.core.entity.R;
import com.mz.common.core.exception.MzException;
import com.netflix.client.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import javax.security.auth.message.AuthException;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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
        ResponseEntity<R<Object>> response = getrResponseEntity(e);
        log.error("登录失败: {}", response.getBody().getMessage());
        return response;
    }

    @NotNull
    public static ResponseEntity<R<Object>> getrResponseEntity(Throwable e) {
        MzErrorCodeEnum oauthAuthException = MzErrorCodeEnum.UNKNOW_EXCEPTION;
        if (e instanceof AuthException || e.getCause() instanceof AuthException) {
            oauthAuthException = MzErrorCodeEnum.OAUTH_EXCEPTION;
        } else if (e instanceof InternalAuthenticationServiceException) {
            if (e.getCause().getCause() instanceof ClientException) {
                oauthAuthException = MzErrorCodeEnum.OAUTH_CLIENT_EXCEPTION;
            }else if(Objects.nonNull(e.getCause()) && e.getCause() instanceof MzException){
                oauthAuthException = null;
            }  else {
                oauthAuthException = MzErrorCodeEnum.OAUTH_AUTH_EXCEPTION;
            }
        } else if (e instanceof InvalidGrantException) {
            oauthAuthException = MzErrorCodeEnum.LOGINACCT_PASSWORD_INVAILD_EXCEPTION;
        } else if (e instanceof InvalidTokenException) {
            oauthAuthException = MzErrorCodeEnum.OAUTH_TOKEN_EXCEPTION;
        } else if (e instanceof UnsupportedGrantTypeException) {
            oauthAuthException = MzErrorCodeEnum.OAUTH_GRANTTYPE_EXCEPTION;
        }else if (e instanceof InvalidRequestException) {
            oauthAuthException = null;
        }
        return ResponseEntity.ok(Objects.nonNull(oauthAuthException) ? R.fail(oauthAuthException) : R.fail(MzErrorCodeEnum.OAUTH_EXCEPTION.getCode(), e.getLocalizedMessage()));
    }

}
