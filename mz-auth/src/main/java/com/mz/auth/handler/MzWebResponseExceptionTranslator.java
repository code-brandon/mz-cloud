package com.mz.auth.handler;

import com.mz.common.core.entity.R;
import com.mz.common.core.exception.MzCodeEnum;
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
        log.warn("登录失败: ", e);
        String message;
        if (e instanceof AuthException || e.getCause() instanceof AuthException) {
            message = e.getMessage();
        } else if (e instanceof InternalAuthenticationServiceException) {
            message = "身份验证失败";
        } else if (e instanceof InvalidGrantException) {
            message = "用户名或密码错误";
        } else if (e instanceof InvalidTokenException) {
            message = "Token无效或过期";
        } else if (e instanceof UnsupportedGrantTypeException) {
            message = "不支持的授予类型";
        } else {
            message = "登录失败";
        }
        return ResponseEntity.ok(R.fail(MzCodeEnum.OAUTH_AUTH_EXCEPTION.getCode(),message));
    }

}
