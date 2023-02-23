package com.mz.auth.service;

import com.google.common.collect.Maps;
import com.mz.common.constant.MzConstant;
import com.mz.common.constant.SecurityConstants;
import com.mz.common.constant.enums.MzErrorCodeEnum;
import com.mz.common.constant.enums.UserStatus;
import com.mz.common.core.context.MzDefaultContextHolder;
import com.mz.common.core.entity.R;
import com.mz.common.core.exception.MzException;
import com.mz.common.core.exception.MzRemoteException;
import com.mz.common.security.entity.MzUserDetailsSecurity;
import com.mz.common.security.service.MzUserDetailsService;
import com.mz.common.utils.MzUtils;
import com.mz.system.api.MzSysUserApi;
import com.mz.system.model.dto.SysUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * What -- Mz 用户详细信息服务实现
 * <br>
 * Describe -- 登录业务处理
 * <br>
 *
 * @Package: com.mz.auth.service
 * @ClassName: MzUserService
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/29 17:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MzUserDetailsServiceImpl implements MzUserDetailsService {

    private final MzSysUserApi mzSysUserApi;

    /**
     * 按用户名加载用户
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        R<SysUserDto> sysUserDtoR = mzSysUserApi.loadUserByUserName(username);
        if (MzConstant.SUCCESS.equals(sysUserDtoR.getCode())) {
            if (Objects.isNull(sysUserDtoR.getData())) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            SysUserDto sysUserDto = sysUserDtoR.getData();
            if (UserStatus.DELETED.getCode().equals(sysUserDto.getDelFlag())) {
                throw new MzException("对不起，您的账号：" + username + " 已被删除");
            } else if (UserStatus.DISABLE.getCode().equals(sysUserDto.getStatus())) {
                throw new MzException("对不起，您的账号：" + username + " 已停用");
            }
            return createLoginUser(sysUserDto);
        }
        throw new MzRemoteException(MzErrorCodeEnum.REMOTE_EXCEPTION.getMsg(), MzErrorCodeEnum.REMOTE_EXCEPTION.getCode());
    }

    /**
     * 查询用户信息
     *
     * @param sysUserDto
     * @return
     */
    private UserDetails createLoginUser(SysUserDto sysUserDto) {
        Collection<GrantedAuthority> authorities = new HashSet<>(5);

        // 判断用户角色是否是管理员
        if (sysUserDto.isIfAdmin()) {
            authorities = AuthorityUtils.createAuthorityList("*:*:*", SecurityConstants.MZ_ROLE + "admin");
        } else {
            Set<String> auth = sysUserDto.getAuthorities();
            // 组装角色
            Set<GrantedAuthority> roles = sysUserDto.getRolePermission().stream().filter(role -> {
                return !StringUtils.isEmpty(role);
            }).map(role -> {
                return new SimpleGrantedAuthority(SecurityConstants.MZ_ROLE + role);
            }).collect(Collectors.toSet());
            if (MzUtils.notEmpty(auth)) {
                List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(sysUserDto.getAuthorities().toArray(new String[sysUserDto.getAuthorities().size()]));
                authorities.addAll(authorityList);
            }
            authorities.addAll(roles);
        }
        MzUserDetailsSecurity detailsSecurity = new MzUserDetailsSecurity();
        BeanUtils.copyProperties(sysUserDto, detailsSecurity);
        detailsSecurity.setAuthorities(authorities);
        Map<@Nullable String, @Nullable Object> map = Maps.newHashMap();
        MzDefaultContextHolder.CONTEXT_HOLDER.get().put("user", detailsSecurity);
        return detailsSecurity;
    }

    @Override
    public UserDetails loadUserByUsername(Map<String, String> parameters) {
        return this.loadUserByUsername(parameters.get("username"));
    }
}
