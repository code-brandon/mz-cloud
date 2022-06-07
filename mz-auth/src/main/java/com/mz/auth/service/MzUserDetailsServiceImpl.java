package com.mz.auth.service;

import com.mz.common.core.constants.Constant;
import com.mz.common.core.constants.SecurityConstants;
import com.mz.common.core.entity.R;
import com.mz.common.core.enums.UserStatus;
import com.mz.common.core.exception.MzBaseException;
import com.mz.common.core.exception.MzCodeEnum;
import com.mz.common.security.entity.MzSysUserSecurity;
import com.mz.common.security.service.MzUserDetailsService;
import com.mz.system.api.MzSysUcerApi;
import com.mz.system.model.dto.SysUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.auth.service
 * @ClassName: MzUserService
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/29 17:03
 */
@Slf4j
@Service
public class MzUserDetailsServiceImpl implements MzUserDetailsService {

    @Autowired
    private MzSysUcerApi mzSysUcerApi;

    /**
     * 按用户名加载用户
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        R<SysUserDto> sysUserDtoR = mzSysUcerApi.loadUserByUserName(username);
        if (Constant.SUCCESS.equals(sysUserDtoR.getCode())) {
            if (Objects.isNull(sysUserDtoR.getData())) {
                log.info("登录用户：{} 不存在.", username);
                throw new UsernameNotFoundException("用户名不存在");
            }
            SysUserDto sysUserDto = sysUserDtoR.getData();
            if (UserStatus.DELETED.getCode().equals(sysUserDto.getDelFlag())) {
                log.info("登录用户：{} 已被删除.", username);
                throw new UsernameNotFoundException("对不起，您的账号：" + username + " 已被删除");
            } else if (UserStatus.DISABLE.getCode().equals(sysUserDto.getStatus())) {
                log.info("登录用户：{} 已被停用.", username);
                throw new UsernameNotFoundException("对不起，您的账号：" + username + " 已停用");
            }
            log.error("{}",sysUserDto);
            return createLoginUser(sysUserDto);
        }
        throw new MzBaseException("nz-auth",String.valueOf(MzCodeEnum.FEIGN_EXCEPTION.getCode()),null,MzCodeEnum.FEIGN_EXCEPTION.getMsg());
    }

    /**
     * 查询用户信息
     *
     * @param sysUserDto
     * @return
     */
    private UserDetails createLoginUser(SysUserDto sysUserDto) {
        Collection<? extends GrantedAuthority> authorities = null;

        // 判断用户角色是否是管理员
        if (sysUserDto.isIfAdmin()) {
            authorities = AuthorityUtils.createAuthorityList("*:*:*", SecurityConstants.MZ_ROLE + "admin");
        } else {
            authorities = AuthorityUtils.createAuthorityList(sysUserDto.getAuthorities().toArray(new String[0]));
        }

        MzSysUserSecurity mzSysUserSecurity = new MzSysUserSecurity(
                sysUserDto.getUserId(),
                sysUserDto.getDeptId(),
                sysUserDto.getLoginName(),
                sysUserDto.getNickName(),
                sysUserDto.getUserType(),
                sysUserDto.getEmail(),
                sysUserDto.getPhonenumber(),
                sysUserDto.getSex(),
                sysUserDto.getStatus(),
                sysUserDto.getLoginIp(),
                sysUserDto.getLoginDate(),
                sysUserDto.getUserName(),
                sysUserDto.getPassword(),
        true, true, true, true,
                authorities);
        log.error("{}",mzSysUserSecurity);
        return mzSysUserSecurity;
    }
}
