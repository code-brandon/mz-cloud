package com.mz.common.security.access;

import cn.hutool.core.util.ArrayUtil;
import com.mz.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * What -- mz 自定义权限访问
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzPermissionAccess
 * @CreateTime 2022/6/9 14:09
 */
@Slf4j
@Component("pms")
public class MzPermissionAccess {

	/** 所有权限标识 */
	private static final String ALL_PERMISSION = "*:*:*";

	/** 管理员角色权限标识 */
	private static final String SUPER_ADMIN = Constant.ADMIN;

	/**
	 * 判断接口是否有任意xxx，xxx权限
	 * @param permissions 权限
	 * @return {boolean}
	 */
	public boolean hasPermission(String... permissions) {
		return isMatch(ALL_PERMISSION, permissions);
	}

	/**
	 * 判断接口是否有任意xxx，xxx权限
	 * @param role 角色
	 * @return {boolean}
	 */
	public boolean hasRole(String... role) {
		return isMatch(SUPER_ADMIN, role);
	}

	private boolean isMatch(String str, String... patterns) {
		if (ArrayUtil.isEmpty(patterns)) {
			return false;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.stream().map(GrantedAuthority::getAuthority).filter(StringUtils::hasText)
				.anyMatch(x -> str.contains(x) || PatternMatchUtils.simpleMatch(patterns, x));
	}

}