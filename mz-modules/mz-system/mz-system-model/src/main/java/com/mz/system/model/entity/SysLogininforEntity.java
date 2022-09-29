package com.mz.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统访问记录
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("系统访问记录")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_logininfor")
public class SysLogininforEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 访问ID
	 */
    @ApiModelProperty("访问ID")
	@TableId(type = IdType.AUTO)
	private Long infoId;
	/**
	 * 用户账号
	 */
    @ApiModelProperty("用户账号")
	private String username;
	/**
	 * 登录IP地址
	 */
    @ApiModelProperty("登录IP地址")
	private String ipaddr;
	/**
	 * 登录地点
	 */
    @ApiModelProperty("登录地点")
	private String loginLocation;
	/**
	 * 浏览器类型
	 */
    @ApiModelProperty("浏览器类型")
	private String browser;
	/**
	 * 操作系统
	 */
    @ApiModelProperty("操作系统")
	private String os;
	/**
	 * 登录状态（0成功 1失败）
	 */
    @ApiModelProperty("登录状态（0成功 1失败）")
	private String status;
	/**
	 * 提示消息
	 */
    @ApiModelProperty("提示消息")
	private String msg;
	/**
	 * 访问时间
	 */
    @ApiModelProperty("访问时间")
	private Date loginTime;

}
