package com.mz.system.model.vo.req;

import com.mz.common.validated.groups.UpdateField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * What -- 部门请求参数
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo.req
 * @ClassName: SysDeptVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/12/24 11:51
 */

@ApiModel("部门请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDeptReqVo implements Serializable {


    /**
     * 部门id
     */

    @NotNull(groups = UpdateField.class)
    @ApiModelProperty("部门id")
    private Long deptId;
    /**
     * 父部门id
     */

    @NotNull
    @ApiModelProperty("父部门id")
    private Long parentId;
    /**
     * 祖级列表
     */
    @ApiModelProperty("祖级列表")
    private String ancestors;
    /**
     * 部门名称
     */

    @NotBlank
    @ApiModelProperty("部门名称")
    private String deptName;
    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    private Integer orderNum;
    /**
     * 负责人
     */
    @ApiModelProperty("负责人")
    private String leader;
    /**
     * 联系电话
     */

    @NotBlank
    @Size(min = 11,max = 11)
    @ApiModelProperty("联系电话")
    private String phone;
    /**
     * 邮箱
     */

    @Email
    @ApiModelProperty("邮箱")
    private String email;
    /**
     * 部门状态（0正常 1停用）
     */
    @ApiModelProperty("部门状态（0正常 1停用）")
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delFlag;
}
