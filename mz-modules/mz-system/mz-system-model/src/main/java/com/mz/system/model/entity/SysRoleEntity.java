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
 * 角色信息表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("角色信息表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
    @ApiModelProperty("角色ID")
	@TableId(type = IdType.AUTO)
	private Long roleId;
	/**
	 * 角色名称
	 */
    @ApiModelProperty("角色名称")
	private String roleName;
	/**
	 * 角色权限字符串
	 */
    @ApiModelProperty("角色权限字符串")
	private String roleKey;
	/**
	 * 显示顺序
	 */
    @ApiModelProperty("显示顺序")
	private Integer roleSort;
	/**
	 * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
	 */
    @ApiModelProperty("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
	private String dataScope;
	/**
	 * 菜单树选择项是否关联显示
	 */
    @ApiModelProperty("菜单树选择项是否关联显示")
	private Integer menuCheckStrictly;
	/**
	 * 部门树选择项是否关联显示
	 */
    @ApiModelProperty("部门树选择项是否关联显示")
	private Integer deptCheckStrictly;
	/**
	 * 角色状态（0正常 1停用）
	 */
    @ApiModelProperty("角色状态（0正常 1停用）")
	private String status;
	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
	private String delFlag;
	/**
	 * 备注
	 */
    @ApiModelProperty("备注")
	private String remark;

}
