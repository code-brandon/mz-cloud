package com.mz.gateway.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What -- 网关路由实体类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzGatewayRoutesEntity
 * @CreateTime 2022/5/20 21:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MzGatewayRoutesEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * URI 路径
     */
    private String uri;

    /**
     * 关键词
     */
    private String predicates;

    /**
     * 过滤器
     */
    private String filters;

    /**
     * 是否有效 1有效 0 无效
     */
    private Integer valid;

}