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
 * 部门表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("部门表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dept")
public class SysDeptEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 部门id
	 */
    @ApiModelProperty("部门id")
	@TableId(type = IdType.AUTO)
	private Long deptId;
	/**
	 * 父部门id
	 */
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
    @ApiModelProperty("联系电话")
	private String phone;
	/**
	 * 邮箱
	 */
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
