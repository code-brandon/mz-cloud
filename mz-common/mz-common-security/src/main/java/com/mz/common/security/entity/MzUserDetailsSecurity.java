package com.mz.common.security.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mz.common.constant.MzConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * What -- Mz 用户详细信息安全
 * <br>
 * Describe -- 默认安全登录用户
 * <br>
 *
 * @Package: com.mz.common.security.entity
 * @ClassName: MzUserDetailsSecurity
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/8/27 20:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("安全登录用户")
public class MzUserDetailsSecurity implements UserDetails, Serializable {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;
    /**
     * 用户类型（00系统用户）
     */
    @ApiModelProperty("用户类型（00系统用户）")
    private String userType;
    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;
    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private String sex;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delFlag;

    /**
     * 是否为超级管理员
     */
    @ApiModelProperty("是否为超级管理员")
    private boolean ifAdmin = true;

    /**
     * 最后登录IP
     */
    @ApiModelProperty("最后登录IP")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    // @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    // @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime loginDate;



    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码")
    @JSONField(serialize = false)
    @JsonIgnore
    private transient String password;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("用户权限列表 （spring security）")
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 角色ID列表
     */
    @ApiModelProperty("角色ID列表（逗号分割）")
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

    /**
     * 账户启用
     */
    @ApiModelProperty("账户启用 （spring security）")
    private boolean enabled= true;

    /**
     * 账户未过期
     */
    @ApiModelProperty("账户未过期  （spring security）")
    private boolean accountNonExpired = true;

    @ApiModelProperty("凭证是否到期（true：未过期）（spring security）")
    @JsonProperty("credentialsNonExpired")
    @JsonAlias("credential")
    private boolean credentialsNonExpired = true;

    @ApiModelProperty("用户是否锁定（true：未锁定）（spring security）")
    @JsonProperty("accountNonLocked")
    @JsonAlias("locked")
    private boolean accountNonLocked = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 帐户未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /**
     * 是帐户未锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /**
     * 凭证未过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /**
     * 用户是否禁用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return !MzConstant.USER_DISABLE.equals(this.status);
    }
}
