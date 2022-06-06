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
 * 通知公告表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("通知公告表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_notice")
public class SysNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 公告ID
	 */
    @ApiModelProperty("公告ID")
	@TableId(type = IdType.AUTO)
	private Integer noticeId;
	/**
	 * 公告标题
	 */
    @ApiModelProperty("公告标题")
	private String noticeTitle;
	/**
	 * 公告类型（1通知 2公告）
	 */
    @ApiModelProperty("公告类型（1通知 2公告）")
	private String noticeType;
	/**
	 * 公告内容
	 */
    @ApiModelProperty("公告内容")
	private String noticeContent;
	/**
	 * 公告状态（0正常 1关闭）
	 */
    @ApiModelProperty("公告状态（0正常 1关闭）")
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
	 * 备注
	 */
    @ApiModelProperty("备注")
	private String remark;

}
