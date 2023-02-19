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
 * 字典数据表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("字典数据表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dict_data")
public class SysDictDataEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字典编码
	 */
    @ApiModelProperty("字典编码")
	@TableId(type = IdType.AUTO)
	private Long dictCode;
	/**
	 * 字典排序
	 */
    @ApiModelProperty("字典排序")
	private Integer dictSort;
	/**
	 * 字典标签
	 */
    @ApiModelProperty("字典标签")
	private String dictLabel;
	/**
	 * 字典键值
	 */
    @ApiModelProperty("字典键值")
	private String dictValue;
	/**
	 * 字典类型
	 */
    @ApiModelProperty("字典类型")
	private String dictType;
	/**
	 * 样式属性（其他样式扩展）
	 */
    @ApiModelProperty("样式属性（其他样式扩展）")
	private String cssClass;
	/**
	 * 表格回显样式
	 */
    @ApiModelProperty("表格回显样式")
	private String listClass;
	/**
	 * 是否默认（Y是 N否）
	 */
    @ApiModelProperty("是否默认（Y是 N否）")
	private String isDefault;
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
