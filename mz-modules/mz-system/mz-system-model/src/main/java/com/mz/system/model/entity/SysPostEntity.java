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
 * 岗位信息表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@ApiModel("岗位信息表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_post")
public class SysPostEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 岗位ID
	 */
    @ApiModelProperty("岗位ID")
	@TableId(type = IdType.AUTO)
	private Long postId;
	/**
	 * 岗位编码
	 */
    @ApiModelProperty("岗位编码")
	private String postCode;
	/**
	 * 岗位名称
	 */
    @ApiModelProperty("岗位名称")
	private String postName;
	/**
	 * 显示顺序
	 */
    @ApiModelProperty("显示顺序")
	private Integer postSort;
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
