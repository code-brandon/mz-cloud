package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysUserRoleEntity;
import com.mz.system.model.vo.SysUserVo;
import com.mz.system.model.vo.req.SysRoleBindUserReqVo;
import com.mz.system.model.vo.search.SysUserSearchVo;

import java.util.Map;
import java.util.Set;

/**
 * 用户和角色关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    PageUtils<SysUserVo> getUserPageByRoleId(Map<String, Object> params, SysUserSearchVo roleIdResVo);

    PageUtils<SysUserVo> getNotThisRoleUserPage(Map<String, Object> params, SysUserSearchVo userByRoleIdResVo);

    boolean saveRoleBindUser(SysRoleBindUserReqVo roleBindUserReqVo);

    boolean deleteByRoleIdAndUserIds(SysRoleBindUserReqVo roleBindUserReqVo);

    /**
     * 按用户 ID 获取角色 ID
     * @param userId
     * @return
     */
    Set<Long> getRoleIdsByUserId(Long userId);

    /**
     * 保存用户角色
     * @param userId
     * @param roleIds
     * @return
     */

    boolean saveUserRoles(Long userId, Set<Long> roleIds);
}

