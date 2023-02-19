package com.mz.system.model.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
 * @ClassName: SysRoleDeptReqVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/10/23 21:40
 */
@ApiModel("角色部门请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDeptReqVo implements Serializable {

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    @NotNull
    private Long roleId;
    /**
     * 部门树选择项是否关联显示
     */
    @ApiModelProperty("部门树选择项是否关联显示")
    private Integer deptCheckStrictly;
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @ApiModelProperty("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope;

    @ApiModelProperty("角色岗位ID集合")
    private Set<Long> deptIds;

}
