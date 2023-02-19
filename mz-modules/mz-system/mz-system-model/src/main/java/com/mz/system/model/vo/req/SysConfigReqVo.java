package com.mz.system.model.vo.req;

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
 * 参数配置表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("参数配置请求类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysConfigReqVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数主键
	 */
    @ApiModelProperty("参数主键")
	@NotNull(groups = {IdField.class, UpdateField.class})
	private Integer configId;
	/**
	 * 参数名称
	 */
    @ApiModelProperty("参数名称")
	@NotBlank
	private String configName;
	/**
	 * 参数键名
	 */
    @ApiModelProperty("参数键名")
	@NotBlank
	private String configKey;
	/**
	 * 参数键值
	 */
    @ApiModelProperty("参数键值")
	@NotBlank
	private String configValue;
	/**
	 * 系统内置（Y是 N否）
	 */
    @ApiModelProperty("系统内置（Y是 N否）")
	@NotBlank
	private String configType;
	/**
	 * 备注
	 */
    @ApiModelProperty("备注")
	private String remark;

}
