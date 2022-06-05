package com.mz.common.sentinel.regular;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;

import javax.servlet.http.HttpServletRequest;

/**
 * What -- 授权规则
 * <br>
 * Describe --
 * 使用 Sentinel 的来源访问控制的功能。来源访问控制根据资源的请求来源（origin）限制资源是否通过
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: RequestOriginParserDefinition
 * @CreateTime 2022/6/3 18:16
 */
public class RequestOriginParserDefinition implements RequestOriginParser {

    /**
     * 来源键
     */
    private static String PER_KEY = "mz_per";

    @Override
    public String parseOrigin(HttpServletRequest request) {
        // 从请求头中获取
        return request.getHeader(PER_KEY);
    }
}