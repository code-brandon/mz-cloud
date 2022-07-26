package com.mz.common.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * What -- 用户权限信息表
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.security.entity
 * @ClassName: MzSysUserSecurity
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/6/7 10:38
 */
@ApiModel("用户权限信息表")
public class MzSysUserSecurity extends User  implements Serializable {

    /**
     * 用户ID
     */
    @Getter
    @Setter
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 部门ID
     */
    @Getter
    @Setter
    @ApiModelProperty("部门ID")
    private Long deptId;

    /**
     * 用户昵称
     */
    @Getter
    @Setter
    @ApiModelProperty("用户昵称")
    private String nickName;
    /**
     * 用户类型（00系统用户）
     */
    @Getter
    @Setter
    @ApiModelProperty("用户类型（00系统用户）")
    private String userType;
    /**
     * 用户邮箱
     */
    @Getter
    @Setter
    @ApiModelProperty("用户邮箱")
    private String email;
    /**
     * 手机号码
     */
    @Getter
    @Setter
    @ApiModelProperty("手机号码")
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @Getter
    @Setter
    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private String sex;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Getter
    @Setter
    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;

    /**
     * 是否为超级管理员
     */
    @Getter
    @Setter
    @ApiModelProperty("是否为超级管理员")
    private boolean ifAdmin;

    /**
     * 最后登录IP
     */
    @Getter
    @Setter
    @ApiModelProperty("最后登录IP")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @Getter
    @Setter
    @ApiModelProperty("最后登录时间")
    private Date loginDate;


    public MzSysUserSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MzSysUserSecurity(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }


    public MzSysUserSecurity(Long userId, Long deptId, String nickName, String userType, String email, String phonenumber, String sex, String status, boolean ifAdmin, String loginIp, Date loginDate,
                             String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.deptId = deptId;
        this.nickName = nickName;
        this.userType = userType;
        this.email = email;
        this.phonenumber = phonenumber;
        this.sex = sex;
        this.status = status;
        this.ifAdmin = ifAdmin;
        this.loginIp = loginIp;
        this.loginDate = loginDate;
    }
}
