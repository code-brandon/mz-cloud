package com.mz.system.model.vo;

import com.mz.common.validated.groups.IdField;
import com.mz.common.validated.groups.UpdateField;
import com.mz.system.model.entity.SysRoleEntity;
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
 * @ClassName: SysRoleVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/10/23 21:40
 */
@ApiModel("角色信息数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleVo extends SysRoleEntity implements Serializable {

    @Override
    @NotNull(groups = {UpdateField.class,IdField.class})
    public Long getRoleId() {
        return super.getRoleId();
    }

    @Override
    @NotBlank
    public String getStatus() {
        return super.getStatus();
    }

    @ApiModelProperty("角色菜单ID集合")
    private Set<Long> menuIds;

    @ApiModelProperty("角色岗位ID集合")
    private Set<Long> deptIds;

}
