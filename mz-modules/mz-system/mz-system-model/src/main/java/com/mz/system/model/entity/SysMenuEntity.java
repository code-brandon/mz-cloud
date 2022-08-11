package com.mz.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mz.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单权限表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("菜单权限表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
public class SysMenuEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
    @ApiModelProperty("菜单ID")
	@TableId(type = IdType.AUTO)
	private Long menuId;
	/**
	 * 菜单名称
	 */
    @ApiModelProperty("菜单名称")
	private String menuName;
	/**
	 * 父菜单ID
	 */
    @ApiModelProperty("父菜单ID")
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
    private String icon;
	/**
	 * 路由地址
	 */
    @ApiModelProperty("路由地址")
	private String path;
	/**
	 * 组件路径
	 */
    @ApiModelProperty("组件路径")
	private String component;
	/**
	 * 路由参数
	 */
    @ApiModelProperty("路由参数")
	private String query;
	/**
	 * 是否为外链（0是 1否）
	 */
    @ApiModelProperty("是否为外链（0是 1否）")
	private Integer isFrame;
	/**
	 * 是否缓存（0缓存 1不缓存）
	 */
    @ApiModelProperty("是否缓存（0缓存 1不缓存）")
	private Integer isCache;
	/**
	 * 菜单类型（M目录 C菜单 F按钮）
	 */
    @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
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
	 * 权限标识
	 */
    @ApiModelProperty("权限标识")
	private String perms;
	/**
	 * 备注
	 */
    @ApiModelProperty("备注")
	private String remark;

}
