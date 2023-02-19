package com.mz.common.log.aspectj;

import cn.hutool.json.JSONUtil;
import com.mz.common.log.annotation.MzLog;
import com.mz.common.log.entity.SysOperLog;
import com.mz.common.log.event.MzLogEvent;
import com.mz.common.security.entity.MzUserDetailsSecurity;
import com.mz.common.security.utils.MzSecurityUtils;
import com.mz.common.utils.MzUtils;
import com.mz.common.utils.MzWebUtils;
import com.mz.common.utils.SpringContextHolderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * What -- 操作日志记录处理
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.log.aspectj
 * @ClassName: MzLogAspectj
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/13 2:04
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class MzLogAspectj {

    @Value("${spring.application.name}")
    private String appName;

    /**
     * MzLog 环绕
     *
     * @param joinPoint 切点
     */
    @Around("@annotation(mzLog)")
    public Object around(ProceedingJoinPoint joinPoint, MzLog mzLog) {
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
        // IP地址
        String remoteAddr = MzWebUtils.getRemoteAddr(request);
        String title = mzLog.title();
        String unknown = Optional.ofNullable(title).orElse(Optional.ofNullable(appName).orElse(""));
        // 实例化计时器
        StopWatch stopWatch = new StopWatch();
        // 开始计时
        stopWatch.start();

        MzUserDetailsSecurity mzSysUserSecurity = MzSecurityUtils.getMzSysUserSecurity();

        SysOperLog sysOperLog = new SysOperLog().setOperIp(remoteAddr)
                .setMethod(className.concat("#").concat(methodName).concat("()"))
                .setBusinessType(mzLog.businessType().ordinal())
                .setOperatorType(mzLog.operatorType().ordinal())
                .setTitle(unknown)
                .setOperName(mzSysUserSecurity.getUsername())
                .setDeptName(mzSysUserSecurity.getDeptName())
                .setOperUrl(request.getRequestURI())
                .setRequestMethod(request.getMethod())
                .setOperTime(LocalDateTime.now());

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            sysOperLog.setErrorMsg(MzUtils.isEmpty(e) ? "" : e.getLocalizedMessage());
        }finally {
            if (mzLog.isSaveRequest() && MzUtils.notEmpty(result)) {
                Map<String, Object> hashMap = new LinkedHashMap<>();
                for (int i = 0; i < args.length; i++) {
                    hashMap.put("args-" + i,args[i]);
                }
                sysOperLog.setOperParam(JSONUtil.toJsonStr(hashMap));
            }
            if (mzLog.isSaveResponse() && MzUtils.notEmpty(result)) {
                if (result.getClass().isPrimitive()) {
                    sysOperLog.setJsonResult(String.valueOf(result));
                }else {
                    sysOperLog.setJsonResult(JSONUtil.toJsonStr(result));
                }
            }
            // 结束计时
            stopWatch.stop();
            long timeMillis = stopWatch.getTotalTimeMillis();
            // 获取毫秒值
            sysOperLog.setTime(timeMillis);
            SpringContextHolderUtils.publishEvent(new MzLogEvent(sysOperLog));
        }
        return result;
    }
}
