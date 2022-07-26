package com.mz.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * What -- 基础实体
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: BaseEntity
 * @CreateTime 2022/6/1 17:25
 */
@Data
@ApiModel("基础信息")
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {

	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建人",example="小政")
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间",example="2022-06-01 17:24:00")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	@ApiModelProperty(value = "更新人",example="小政")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间",example="2022-06-01 17:24:00")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

}
