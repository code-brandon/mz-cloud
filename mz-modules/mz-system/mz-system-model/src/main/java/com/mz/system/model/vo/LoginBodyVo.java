package com.mz.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * What -- 用户登录对象
 * <br>
 * Describe --
 * <br>
 * @ClassName LoginBodyVo
 * @author 小政同学  QQ:xiaozheng666888@qq.com
 * @CreateTime 2021/11/16 17:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户登录表")
public class LoginBodyVo implements Serializable {
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    @NotBlank
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty("验证码")
    private String code;

    /**
     * 唯一标识
     */
    @ApiModelProperty("唯一标识")
    private String uuid = "";
}