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
 * 字典类型表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("字典类型表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dict_type")
public class SysDictTypeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字典主键
	 */
    @ApiModelProperty("字典主键")
	@TableId(type = IdType.AUTO)
	private Long dictId;
	/**
	 * 字典名称
	 */
    @ApiModelProperty("字典名称")
	private String dictName;
	/**
	 * 字典类型
	 */
    @ApiModelProperty("字典类型")
	private String dictType;
	/**
	 * 状态（0正常 1停用）
	 */
    @ApiModelProperty("状态（0正常 1停用）")
	private String status;
	/**
	 * 备注
	 */
    @ApiModelProperty("备注")
	private String remark;

}
