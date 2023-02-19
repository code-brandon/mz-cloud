package com.mz.system.api;

import com.mz.common.constant.SecurityConstants;
import com.mz.common.core.entity.R;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.dto.SysUserLoginLogDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @ClassName: MzSysUserApi
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/7 10:27
 */
@FeignClient(contextId = "mzSysUcerApi", value = "mz-system-provider")
public interface MzSysUserApi {
    /**
     * 按用户名获取用户信息 (登录暴漏接口)
     *
     * @param userName 用户名
     * @return 单条数据
     */
    @PostMapping(value = "/admin/sysuser/getUserInfoByUserName", headers = SecurityConstants.MZ_FROM_KV)
    R<SysUserDto> loadUserByUserName(@Valid @RequestParam(value = "username") @NotBlank String userName);

    /**
     * 修改登录记录
     *
     * @param sysUserLoginLogDto
     * @return
     */
    @PutMapping(value = "/admin/sysuser/update/login/log", headers = SecurityConstants.MZ_FROM_KV)
    R<Boolean> updateLoginLog(@Validated @RequestBody SysUserLoginLogDto sysUserLoginLogDto);

}
