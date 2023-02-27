package com.mz.system.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mz.system.model.entity.SysUserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName SysUserDto
 * @CreateTime 2021/11/15 11:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = "handler")
public class SysUserDto extends SysUserEntity implements  Serializable {
    /**
     * 权限列表
     */
    @ApiModelProperty("权限列表")
    private Set<String> authorities;

    /**
     * 角色权限列表
     */
    @ApiModelProperty("角色列表（逗号分割）")
    private String rolePermission;

    /**
     * 是否为超级管理员
     */
    @ApiModelProperty("是否为超级管理员")
    private boolean ifAdmin;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 角色ID列表
     */
    @ApiModelProperty("数据权限范围（逗号分割）")
    private String roleIds;
    /**
     * 数据权限范围
     */
    @ApiModelProperty("数据权限范围（逗号分割）")
    private String dataScopes;
    /**
     * 岗位ID列表
     */
    @ApiModelProperty("岗位ID列表（逗号分割）")
    private String postIds;
    /**
     * 岗位名称列表
     */
    @ApiModelProperty("岗位名称列表（逗号分割）")
    private String postNames;

}
