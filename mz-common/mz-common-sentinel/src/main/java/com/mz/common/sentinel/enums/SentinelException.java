package com.mz.common.sentinel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * What --  sentinel 限流异常枚举
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.sentinel.enums
 * @ClassName: SentinelException
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/4 19:03
 */
@Getter
@AllArgsConstructor
public enum  SentinelException {
    FLOWEXCEPTION(11000, "FlowException","限流了"),
    PARAMFLOWEXCEPTION(11001, "ParamFlowException","参数限流了"),
    SYSTEMBLOCKEXCEPTION(11002, "SystemBlockException","系统负载异常了"),
    AUTHORITYEXCEPTION(11003, "AuthorityException","授权异常了"),
    DEGRADEEXCEPTION(11004, "DegradeException","降级了"),;

    private final int code;
    private final String value;
    private final String msg;
}
