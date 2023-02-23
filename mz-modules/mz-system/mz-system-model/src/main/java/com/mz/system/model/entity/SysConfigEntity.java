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
 * 参数配置表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("参数配置表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_config")
public class SysConfigEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数主键
	 */
    @ApiModelProperty("参数主键")
	@TableId(type = IdType.AUTO)
	private Long configId;
	/**
	 * 参数名称
	 */
    @ApiModelProperty("参数名称")
	private String configName;
	/**
	 * 参数键名
	 */
    @ApiModelProperty("参数键名")
	private String configKey;
	/**
	 * 参数键值
	 */
    @ApiModelProperty("参数键值")
	private String configValue;
	/**
	 * 系统内置（Y是 N否）
	 */
    @ApiModelProperty("系统内置（Y是 N否）")
	private String configType;
	/**
	 * 备注
	 */
    @ApiModelProperty("备注")
	private String remark;

}
