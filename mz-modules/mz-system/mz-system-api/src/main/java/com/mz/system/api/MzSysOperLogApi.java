package com.mz.system.api;

import com.mz.common.constant.SecurityConstants;
import com.mz.common.core.entity.R;
import com.mz.system.model.dto.SysOperLogDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * What -- 业务日志远程调用接口
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.api
 * @ClassName: MzSysUserApi
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/7 10:27
 */
@FeignClient(contextId = "mzSysOperLogApi", value = "mz-system-provider")
public interface MzSysOperLogApi {
    /**
     * 保存数据
     * @param sysOperLog 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/admin/sysoperlog/save", headers = SecurityConstants.MZ_FROM_KV)
    R<Boolean> save(@RequestBody SysOperLogDto sysOperLog);

}
