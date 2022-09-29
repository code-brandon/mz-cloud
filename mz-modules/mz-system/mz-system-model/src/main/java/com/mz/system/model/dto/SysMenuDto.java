package com.mz.system.model.dto;

import com.mz.system.model.entity.SysMenuEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * What -- 菜单 Dto
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.dto
 * @ClassName: SysMenuDto
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/23 18:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuDto extends SysMenuEntity {
    /** 子菜单 */
    @ApiModelProperty("子菜单")
    private List<SysMenuDto> children = new ArrayList<SysMenuDto>();
}
