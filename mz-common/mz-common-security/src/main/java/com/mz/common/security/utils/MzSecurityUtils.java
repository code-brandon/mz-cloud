package com.mz.common.security.utils;


import cn.hutool.core.util.StrUtil;
import com.mz.common.core.constants.SecurityConstants;
import com.mz.common.security.entity.MzSysUserSecurity;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * What -- 安全工具类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzSecurityUtils
 * @CreateTime 2022/6/21 23:23
 */
@UtilityClass
public class MzSecurityUtils {

	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 */
	public MzSysUserSecurity getMzSysUserSecurity(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof MzSysUserSecurity) {
			return (MzSysUserSecurity) principal;
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public MzSysUserSecurity getMzSysUserSecurity() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getMzSysUserSecurity(authentication);
	}

	/**
	 * 获取用户角色信息
	 * @return 角色集合
	 */
	public List<Long> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<Long> roleIds = new ArrayList<>();
		authorities.stream().filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.MZ_ROLE))
				.forEach(granted -> {
					String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.MZ_ROLE);
					roleIds.add(Long.parseLong(id));
				});
		return roleIds;
	}

}
