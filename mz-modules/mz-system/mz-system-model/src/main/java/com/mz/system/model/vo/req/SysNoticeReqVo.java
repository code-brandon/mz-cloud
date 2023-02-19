package com.mz.system.model.vo.req;

import com.mz.common.mybatis.entity.BaseEntity;
import com.mz.common.validated.groups.IdField;
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
 * 通知公告通知公告请求Vo
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("通知公告请求Vo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysNoticeReqVo extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 公告ID
	 */
    @ApiModelProperty("公告ID")
	@NotNull(groups = {IdField.class, UpdateField.class})
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
	@NotBlank
	private String status;
	/**
	 * 备注
	 */
    @ApiModelProperty("备注")
	private String remark;

}
