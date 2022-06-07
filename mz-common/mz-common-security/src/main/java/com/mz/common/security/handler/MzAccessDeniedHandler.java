package com.mz.common.security.handler;

import com.alibaba.fastjson.JSON;
import com.mz.common.core.entity.R;
import com.mz.common.core.exception.MzCodeEnum;
import com.mz.common.core.utils.MzWebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * What -- Mz 访问被拒绝处理程序 （资源服务器）
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.security.handler
 * @ClassName: MzAccessDeniedHandler
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/7 18:08
 */
@Component
public class MzAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        MzWebUtils.renderJson(response, JSON.toJSONString(R.error(MzCodeEnum.OAUTH_ACCESS_EXCEPTION.getCode(), MzCodeEnum.OAUTH_ACCESS_EXCEPTION.getMsg() + accessDeniedException.getMessage())));
    }
}
