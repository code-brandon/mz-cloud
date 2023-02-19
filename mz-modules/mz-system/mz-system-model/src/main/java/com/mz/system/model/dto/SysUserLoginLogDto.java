package com.mz.system.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName SysUserDto
 * @CreateTime 2021/11/15 11:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserLoginLogDto implements  Serializable {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    @NotNull
    private Long userId;

    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String username;

    /**
     * 最后登录IP
     */
    @ApiModelProperty("最后登录IP")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    @NotNull
    private LocalDateTime loginDate;

}
