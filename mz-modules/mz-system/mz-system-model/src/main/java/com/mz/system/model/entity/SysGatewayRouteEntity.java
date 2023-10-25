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
 * @description 网关路由表
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @date 2023-09-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("网关路由表")
@TableName("sys_gateway_route")
public class SysGatewayRouteEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * 路由id
    */
    @ApiModelProperty("路由id")
    @TableId(type = IdType.INPUT)
    private String routeId;

    /**
    * 路由名称
    */
    @ApiModelProperty("路由名称")
    private String routeName;

    /**
    * 谓词定义
    */
    @ApiModelProperty("谓词定义")
    private String predicates;

    /**
    * 过滤器配置
    */
    @ApiModelProperty("过滤器配置")
    private String filters;

    /**
     * 额外数据
     */
    @ApiModelProperty("额外数据")
    private String metadata;

    /**
    * 路由路径配置
    */
    @ApiModelProperty("路由路径配置")
    private String routeUri;

    /**
    * 路由状态（0正常 1停用）
    */
    @ApiModelProperty("路由状态（0正常 1停用）")
    private String status;

    /**
    * 删除标志（0代表存在 2代表删除）
    */
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delFlag;

    /**
    * 备注
    */
    @ApiModelProperty("备注")
    private String remark;

    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer orderNum;
}