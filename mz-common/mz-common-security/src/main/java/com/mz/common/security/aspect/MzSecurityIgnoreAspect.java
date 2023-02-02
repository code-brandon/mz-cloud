package com.mz.common.security.aspect;

import cn.hutool.core.util.StrUtil;
import com.mz.common.constant.SecurityConstants;
import com.mz.common.security.annotation.Ignore;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

/**
 * What -- 安全忽略切面
 * <br>
 * Describe -- 设计理念仿照PIG开源框架
 * 我们使用一个Spring-AOP，在对所有Ignore注解的方法做一个环绕增强的切点，进行统一的处理。
 * 在上面我们提到的Ignore的value参数，当该参数为true时，对方法的入参进行判断，
 * 仅当符合我们定制的入参规则时（@RequestHeader(SecurityConstants.MZ_FROM) 与SecurityConstants.MZ_FROM_IN做比较）,
 * 对它进行放行，不符合时，抛出异常；当value为false时，咱不做任何处理，此时Ignore仅起到了一个ignore-url的作用。
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzSecurityIgnoreAspect
 * @CreateTime 2022/6/2 17:06
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class MzSecurityIgnoreAspect {

	private final HttpServletRequest request;

	@SneakyThrows
	@Around("@annotation(ignore)")
	public Object around(ProceedingJoinPoint point, Ignore ignore) {
		String header = request.getHeader(SecurityConstants.MZ_FROM);
		if (ignore.value() && !StrUtil.equals(SecurityConstants.MZ_FROM_IN, header)) {
			log.warn("访问接口 {} 没有权限", point.getSignature().getName());
			throw new AccessDeniedException("Access is denied");
		}
		return point.proceed();
	}

}