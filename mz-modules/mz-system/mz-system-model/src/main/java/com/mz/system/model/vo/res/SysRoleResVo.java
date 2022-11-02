package com.mz.system.model.vo.res;

import com.mz.system.model.entity.SysRoleEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo
 * @ClassName: SysRoleResVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/10/23 21:40
 */
@Data
@RequiredArgsConstructor
public class SysRoleResVo extends SysRoleEntity implements Serializable {

    @ApiModelProperty("角色菜单ID集合")
    private List<Long> menuIds;

    @ApiModelProperty("角色岗位ID集合")
    private List<Long> deptIds;

}
