package com.mz.auth.aspectj;

import com.mz.auth.handler.MzWebResponseExceptionTranslator;
import com.mz.common.constant.MzConstant;
import com.mz.common.core.context.MzDefaultContextHolder;
import com.mz.common.core.entity.R;
import com.mz.common.security.entity.MzUserDetailsSecurity;
import com.mz.common.utils.IPSearcherUtils;
import com.mz.common.utils.MzWebUtils;
import com.mz.system.api.MzSysLogininforApi;
import com.mz.system.api.MzSysUserApi;
import com.mz.system.model.dto.SysLogininforDto;
import com.mz.system.model.dto.SysUserLoginLogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * What --  Oauth 切面
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.auth.aspectj
 * @ClassName: MzOauthAspectj
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/19 18:29
 */
@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class MzOauthAspectj {

    private final MzSysLogininforApi mzSysLogininforApi;
    private final MzSysUserApi mzSysUserApi;

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.*.*(..)) && @annotation(requestMapping)")
    public Object oauthAspectj(ProceedingJoinPoint joinPoint, RequestMapping requestMapping) throws Throwable {
        LocalDateTime nowTime = LocalDateTime.now();

        // 参数列表
        Object[] args = joinPoint.getArgs();
        // 目标方法
        Object target = joinPoint.getTarget();
        Signature signature = joinPoint.getSignature();
        //类名
        String className = target.getClass().getName();
        // 方法名
        String methodName = signature.getName();
        HttpServletRequest request = MzWebUtils.getRequest();
        String userAgent = request.getHeader("User-Agent");
        // IP地址
        String remoteAddr = MzWebUtils.getRemoteAddr(request);
        // 浏览器携带的信息
        Map<String, String> parseUserAgent = MzWebUtils.parseUserAgent(userAgent);
        // 路径
        String[] paths = requestMapping.path();
        String[] values = requestMapping.value();
        String[] path = paths.length > 0 ? paths : values.length > 0 ? values : new String[]{request.getRequestURI()};
        // 实例化计时器
        StopWatch stopWatch = new StopWatch();
        // 开始计时
        stopWatch.start();

        SysLogininforDto logininfor = new SysLogininforDto();
        logininfor.setLoginTime(nowTime);
        logininfor.setIpaddr(remoteAddr);
        logininfor.setOs(parseUserAgent.getOrDefault("os", ""));
        logininfor.setBrowser(parseUserAgent.getOrDefault("browser", ""));

        // 获取用户名
        if (args.length == 2) {
            if (args[1] instanceof Map) {
                String username =  (String) ((Map) args[1]).getOrDefault("username", "");
                logininfor.setUsername(username);
            }
        }
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            logininfor.setStatus(MzConstant.SUCCESS.toString());
            logininfor.setMsg("登录成功!");
            MzUserDetailsSecurity userSecurity = (MzUserDetailsSecurity) MzDefaultContextHolder.CONTEXT_HOLDER.get().get("user");
            // 异步发送用户登录记录更新请求
            CompletableFuture.runAsync(() -> {
                SysUserLoginLogDto userLogDto = new SysUserLoginLogDto(userSecurity.getUserId(), null, remoteAddr, nowTime);
                mzSysUserApi.updateLoginLog(userLogDto);
            });
        } catch (Throwable e) {
            ResponseEntity<R<Object>> exTranslation = MzWebResponseExceptionTranslator.getrResponseEntity(e);
            logininfor.setStatus(MzConstant.FAIL.toString());
            logininfor.setMsg(exTranslation.getBody().getMessage());
            throw e;
        }finally {
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            // 异步发送系统登录记录请求
            CompletableFuture.runAsync(() -> {
                logininfor.setLoginLocation(IPSearcherUtils.searcher(logininfor.getIpaddr()));
                mzSysLogininforApi.save(logininfor);
                log.debug("类名：{},方法名：{},IP地址：{},路径：{},耗时：{}", className, methodName, remoteAddr,path, totalTimeMillis);
            });
            MzDefaultContextHolder.CONTEXT_HOLDER.remove();
        }

        return proceed;
    }
}
