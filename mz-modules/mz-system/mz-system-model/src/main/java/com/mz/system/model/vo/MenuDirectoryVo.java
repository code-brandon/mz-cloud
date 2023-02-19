package com.mz.system.model.vo;

import com.mz.common.validated.groups.UpdateField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * What -- 菜单目录信息
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo
 * @ClassName: MenuButtonVo
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/12/18 17:47
 */
@ApiModel("菜单目录信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDirectoryVo implements Serializable {
    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    @NotNull(groups = UpdateField.class)
    private Long menuId;
    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    @NotBlank
    private String menuName;
    /**
     * 父菜单ID
     */
    @ApiModelProperty("父菜单ID")
    @NotNull
    private Long parentId;
    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    private Integer orderNum;
    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    @NotBlank
    private String icon;
    /**
     * 路由地址
     */
    @ApiModelProperty("路由地址")
    private String path;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
    @NotBlank
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    @ApiModelProperty("菜单状态（0显示 1隐藏）")
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    @ApiModelProperty("菜单状态（0正常 1停用）")
    private String status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
