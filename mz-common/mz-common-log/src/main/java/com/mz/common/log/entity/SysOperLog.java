package com.mz.common.log.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * What -- 操作日志记录
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: SysOperLog
 * @CreateTime 2023/2/14 21:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysOperLog implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 日志主键
	 */
	private Long operId;
	/**
	 * 模块标题
	 */
	private String title;
	/**
	 * 业务类型（0其它 1新增 2修改 3删除）
	 */
	private Integer businessType;
	/**
	 * 方法名称
	 */
	private String method;
	/**
	 * 请求方式
	 */
	private String requestMethod;
	/**
	 * 操作类别（0其它 1后台用户 2手机端用户）
	 */
	private Integer operatorType;
	/**
	 * 操作人员
	 */
	private String operName;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 请求URL
	 */
	private String operUrl;
	/**
	 * 主机地址
	 */
	private String operIp;
	/**
	 * 操作地点
	 */
	private String operLocation;
	/**
	 * 请求参数
	 */
	private String operParam;
	/**
	 * 返回参数
	 */
	private String jsonResult;
	/**
	 * 操作状态（0正常 1异常）
	 */
	private Integer status;
	/**
	 * 错误消息
	 */
	private String errorMsg;

	/**
	 * 耗时 单位毫秒
	 */
	private Long time;
	/**
	 * 操作时间
	 */
	private LocalDateTime operTime;

}