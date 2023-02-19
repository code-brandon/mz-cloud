package com.mz.system.api;

import com.mz.common.constant.SecurityConstants;
import com.mz.common.core.entity.R;
import com.mz.system.model.dto.SysLogininforDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * What -- 登录日志远程调用接口
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.api
 * @ClassName: MzSysUserApi
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/7 10:27
 */
@FeignClient(contextId = "mzSysLogininforApi", value = "mz-system-provider")
public interface MzSysLogininforApi {

    /**
     * 保存数据
     *
     * @param sysLogininfor 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping(value = "/admin/syslogininfor/save", headers = SecurityConstants.MZ_FROM_KV)
    R<Boolean> save(@RequestBody SysLogininforDto sysLogininfor);

}
