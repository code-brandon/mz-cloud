package com.mz.system.model.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * What -- 系统用户按角色ID请求实体
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo.res
 * @ClassName: SysUserByRoleIdReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/12/9 21:01
 */
@ApiModel("系统用户按角色ID请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserByRoleIdReqVo implements Serializable {
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    @NotNull
    private Long roleId;

    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String username;
    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phonenumber;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;
}
