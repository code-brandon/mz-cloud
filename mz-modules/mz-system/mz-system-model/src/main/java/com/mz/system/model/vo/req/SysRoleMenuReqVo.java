package com.mz.system.model.vo.req;

import com.mz.common.validated.groups.IdField;
import com.mz.common.validated.groups.UpdateField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo
 * @ClassName: SysRoleMenuReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/10/23 21:40
 */
@ApiModel("角色菜单请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleMenuReqVo implements Serializable {

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    @NotNull(groups = {IdField.class, UpdateField.class})
    private Long roleId;
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @NotBlank
    private String roleName;
    /**
     * 角色权限字符串
     */
    @ApiModelProperty("角色权限字符串")
    @NotBlank
    private String roleKey;
    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    @NotNull
    private Integer roleSort;
    /**
     * 菜单树选择项是否关联显示
     */
    @ApiModelProperty("菜单树选择项是否关联显示")
    @NotNull
    private Integer menuCheckStrictly;
    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty("角色状态（0正常 1停用）")
    @NotBlank
    private String status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("菜单ID集合")
    private Set<Long> menuIds;

}
