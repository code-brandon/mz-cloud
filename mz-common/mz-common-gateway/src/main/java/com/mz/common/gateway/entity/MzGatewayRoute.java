package com.mz.common.gateway.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 网关路由表
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @date 2023-09-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("网关路由")
public class MzGatewayRoute implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * 路由id
    */
    @ApiModelProperty("路由id")
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
    private List<MzPredicateDefinition> predicates = new ArrayList<>();

    /**
     * 额外数据
     */
    @ApiModelProperty("额外数据")
    private Map<String, Object> metadata = new HashMap<>();

    /**
    * 过滤器配置
    */
    @ApiModelProperty("过滤器配置")
    private List<MzFilterDefinition> filters = new ArrayList<>();

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
    * 创建者
    */
    @ApiModelProperty(value = "创建人",example="小政")
    private String createBy;

    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间",example="2022-06-01 17:24:00")
    private LocalDateTime createTime;

    /**
    * 更新者
    */
    @ApiModelProperty(value = "更新人",example="小政")
    private String updateBy;

    /**
    * 更新时间
    */
    @ApiModelProperty(value = "更新时间",example="2022-06-01 17:24:00")
    private LocalDateTime updateTime;

    /**
    * 备注
    */
    @ApiModelProperty("备注")
    private String remark;

    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer order;
}