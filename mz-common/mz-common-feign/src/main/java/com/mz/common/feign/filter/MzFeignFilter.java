package com.mz.common.feign.filter;

import com.mz.common.feign.context.MzFeignContextHolder;
import com.mz.common.feign.ribbon.MzFeignRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * 自定义Filter <br>
 * 对请求的header过滤 <br>
 * 拦截顺序：filter—>Interceptor-->ControllerAdvice-->@Aspect -->Controller <br>
 * 过滤器Filter可以拿到原始的HTTP请求和响应的信息，
 *     但是拿不到你真正处理请求方法的信息，也就是方法的信息 <br>
 * 配合{@link MzFeignContextHolder}使用，在{@link  MzFeignRule}进行处理 <br>
 * @Component 注解让拦截器注入Bean，从而让拦截器生效
 * @WebFilter 配置拦截规则
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzFeignFilter
 */
@Slf4j
@Component
@WebFilter(urlPatterns = {"/**"},filterName = "mzFeignFilter")
public class MzFeignFilter implements Filter {
 
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("MzFeignFilter init {}",filterConfig.getFilterName());
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("MzFeignFilter doFilter 我拦截到了请求");
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        Map<String, Object> map = MzFeignContextHolder.CONTEXT_HOLDER.get();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            map.put(headerName, servletRequest.getHeader(headerName));
        }
        chain.doFilter(request,response);
        MzFeignContextHolder.CONTEXT_HOLDER.remove();
    }
 
    @Override
    public void destroy() {
        log.info("MzFeignFilter destroy");
    }
}
