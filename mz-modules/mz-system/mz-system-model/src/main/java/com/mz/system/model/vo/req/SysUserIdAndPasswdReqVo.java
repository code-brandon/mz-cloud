package com.mz.system.model.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * What -- 用户Id和密码的Vo
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo
 * @ClassName: SysUserIdAndPasswdReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/10/23 19:24
 */
@ApiModel("ID和密码请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserIdAndPasswdReqVo {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    @NotBlank
    private Long userId;

    /**
     * 密码
     */
    @NotBlank
    @ApiModelProperty("密码")
    private String password;
}
