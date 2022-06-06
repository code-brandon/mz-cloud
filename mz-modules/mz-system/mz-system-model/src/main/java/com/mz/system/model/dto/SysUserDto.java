package com.mz.system.model.dto;

import com.mz.system.model.entity.SysUserEntity;
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
public class SysUserDto extends SysUserEntity implements  Serializable {
    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 校色列表
     */
    private Set<String> rolePermission;

    /**
     * 是否为超级管理员
     */
    private boolean ifAdmin;

}
