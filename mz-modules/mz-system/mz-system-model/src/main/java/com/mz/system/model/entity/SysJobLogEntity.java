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
 * 定时任务调度日志表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("定时任务调度日志表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_job_log")
public class SysJobLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务日志ID
	 */
    @ApiModelProperty("任务日志ID")
	@TableId(type = IdType.AUTO)
	private Long jobLogId;
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
	 * 日志信息
	 */
    @ApiModelProperty("日志信息")
	private String jobMessage;
	/**
	 * 执行状态（0正常 1失败）
	 */
    @ApiModelProperty("执行状态（0正常 1失败）")
	private String status;
	/**
	 * 异常信息
	 */
    @ApiModelProperty("异常信息")
	private String exceptionInfo;
	/**
	 * 创建时间
	 */
    @ApiModelProperty("创建时间")
	private Date createTime;

}
