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
import java.time.LocalDateTime;

/**
 * 用户信息表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("用户信息表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SysUserEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
    @ApiModelProperty("用户ID")
	@TableId(type = IdType.AUTO)
	private Long userId;
	/**
	 * 部门ID
	 */
    @ApiModelProperty("部门ID")
	private Long deptId;
	/**
	 * 用户账号
	 */
    @ApiModelProperty("用户账号")
	private String username;
	/**
	 * 用户昵称
	 */
    @ApiModelProperty("用户昵称")
	private String nickName;
	/**
	 * 用户类型（00系统用户）
	 */
    @ApiModelProperty("用户类型（00系统用户）")
	private String userType;
	/**
	 * 用户邮箱
	 */
    @ApiModelProperty("用户邮箱")
	private String email;
	/**
	 * 手机号码
	 */
    @ApiModelProperty("手机号码")
	private String phonenumber;
	/**
	 * 用户性别（0男 1女 2未知）
	 */
    @ApiModelProperty("用户性别（0男 1女 2未知）")
	private String sex;
	/**
	 * 头像地址
	 */
    @ApiModelProperty("头像地址")
	private String avatar;
	/**
	 * 密码
	 */
	@ApiModelProperty("密码")
	private String password;
	/**
	 * 帐号状态（0正常 1停用）
	 */
    @ApiModelProperty("帐号状态（0正常 1停用）")
	private String status;
	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
	private String delFlag;
	/**
	 * 最后登录IP
	 */
    @ApiModelProperty("最后登录IP")
	private String loginIp;
	/**
	 * 最后登录时间
	 */
    @ApiModelProperty("最后登录时间")
	private LocalDateTime loginDate;
	/**
	 * 备注
	 */
    @ApiModelProperty("备注")
	private String remark;

}
