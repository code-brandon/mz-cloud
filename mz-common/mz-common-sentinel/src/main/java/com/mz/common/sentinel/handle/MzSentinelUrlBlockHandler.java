package com.mz.common.sentinel.handle;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mz.common.core.constants.Constant;
import com.mz.common.core.entity.R;
import com.mz.common.sentinel.enums.SentinelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * What --  自定义异常返回
 * <br>
 * Describe -- BlockException 异常接口,包含Sentinel的五个异常 <br>
 * FlowException 限流异常 <br>
 * DegradeException 降级异常 <br>
 * ParamFlowException 参数限流异常 <br>
 * AuthorityException 授权异常 <br>
 * SystemBlockException 系统负载异常
 * <br>
 *
 * @Package: com.mz.common.sentinel.handle
 * @ClassName: MzSentinelUrlBlockHandler
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/3 18:22
 */
@Slf4j
public class MzSentinelUrlBlockHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
        String errMsg = "";
        int errCode = Constant.FAIL;

        String errName = e.getClass().getName();
        int indexOf = errName.lastIndexOf(".");
        errName = errName.substring(indexOf != -1 ? indexOf + 1 : 0);

        switch (SentinelException.valueOf(errName.toUpperCase())) {
            case FLOWEXCEPTION:
                errMsg = SentinelException.FLOWEXCEPTION.getMsg();
                errCode = SentinelException.FLOWEXCEPTION.getCode();
                break;
            case PARAMFLOWEXCEPTION:
                errMsg = SentinelException.PARAMFLOWEXCEPTION.getMsg();
                errCode = SentinelException.PARAMFLOWEXCEPTION.getCode();
                break;
            case SYSTEMBLOCKEXCEPTION:
                errMsg = SentinelException.SYSTEMBLOCKEXCEPTION.getMsg();
                errCode = SentinelException.SYSTEMBLOCKEXCEPTION.getCode();
                break;
            case AUTHORITYEXCEPTION:
                errMsg = SentinelException.AUTHORITYEXCEPTION.getMsg();
                errCode = SentinelException.AUTHORITYEXCEPTION.getCode();
                break;
            case DEGRADEEXCEPTION:
                errMsg = SentinelException.DEGRADEEXCEPTION.getMsg();
                errCode = SentinelException.DEGRADEEXCEPTION.getCode();
                break;
            default:
                errMsg = "未知异常";
                break;
        }

        log.info("sentinel {} 资源名称{}", StringUtils.isEmpty(e.getMessage()) ? errMsg : e.getMessage(), e.getRule().getResource());
        response.setContentType(ContentType.JSON.toString(Charset.forName(String.valueOf(StandardCharsets.UTF_8))));
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.getWriter().print(JSONUtil.toJsonStr(R.fail(errCode,errMsg)));
    }
}
