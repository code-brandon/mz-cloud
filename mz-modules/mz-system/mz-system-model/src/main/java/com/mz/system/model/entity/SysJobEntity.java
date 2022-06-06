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
 * 定时任务调度表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("定时任务调度表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_job")
public class SysJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务ID
	 */
    @ApiModelProperty("任务ID")
	@TableId(type = IdType.AUTO)
	private Long jobId;
	/**
	 * 任务名称
	 */
    @ApiModelProperty("任务名称")
	private String jobName;
	/**
	 * 任务组名
	 */
    @ApiModelProperty("任务组名")
	private String jobGroup;
	/**
	 * 调用目标字符串
	 */
    @ApiModelProperty("调用目标字符串")
	private String invokeTarget;
	/**
	 * cron执行表达式
	 */
    @ApiModelProperty("cron执行表达式")
	private String cronExpression;
	/**
	 * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
	 */
    @ApiModelProperty("计划执行错误策略（1立即执行 2执行一次 3放弃执行）")
	private String misfirePolicy;
	/**
	 * 是否并发执行（0允许 1禁止）
	 */
    @ApiModelProperty("是否并发执行（0允许 1禁止）")
	private String concurrent;
	/**
	 * 状态（0正常 1暂停）
	 */
    @ApiModelProperty("状态（0正常 1暂停）")
	private String status;
	/**
	 * 创建者
	 */
    @ApiModelProperty("创建者")
	private String createBy;
	/**
	 * 创建时间
	 */
    @ApiModelProperty("创建时间")
	private Date createTime;
	/**
	 * 更新者
	 */
    @ApiModelProperty("更新者")
	private String updateBy;
	/**
	 * 更新时间
	 */
    @ApiModelProperty("更新时间")
	private Date updateTime;
	/**
	 * 备注信息
	 */
    @ApiModelProperty("备注信息")
	private String remark;

}
