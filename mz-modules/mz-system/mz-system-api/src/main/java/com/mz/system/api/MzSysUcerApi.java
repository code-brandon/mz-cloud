package com.mz.system.api;

import com.mz.common.core.entity.R;
import com.mz.system.model.dto.SysUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * What -- 用户远程调用接口
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.api
 * @ClassName: MzSysUcerApi
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/7 10:27
 */
@FeignClient("mz-system-provider")
public interface MzSysUcerApi {
    /**
     * 按用户名获取用户信息 (登录暴漏接口)
     *
     * @param userName 用户名
     * @return 单条数据
     */
    @PostMapping("/admin/sysuser/getUserInfoByUserName")
    R<SysUserDto> loadUserByUserName(@Valid @RequestParam(value = "username") @NotBlank String userName);

}
