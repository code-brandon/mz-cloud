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

    /**
     * 根据角色ID查询用户分页数据
     *
     * @param params       分页数据
     * @param userSearchVo 分页条件
     * @return 分页信息
     */
    PageUtils<SysUserVo> getUserPageByRoleId(Map<String, Object> params, SysUserSearchVo userSearchVo);

    /**
     * 根据角色ID查询不是此角色用户分页数据
     *
     * @param params       分页数据
     * @param userSearchVo 分页条件
     * @return 分页信息
     */
    PageUtils<SysUserVo> getNotThisRoleUserPage(Map<String, Object> params, SysUserSearchVo userSearchVo);

    /**
     * 保存角色绑定用户的关系
     *
     * @param roleBindUserReqVo 角色绑定用户请求 Vo
     * @return true：成功，false：失败
     */
    boolean saveRoleBindUser(SysRoleBindUserReqVo roleBindUserReqVo);

    /**
     * 按角色 ID和用户ID集合删除
     *
     * @param roleBindUserReqVo 角色绑定用户请求 Vo
     * @return true：成功，false：失败
     */
    boolean deleteByRoleIdAndUserIds(SysRoleBindUserReqVo roleBindUserReqVo);

    /**
     * 按用户 ID 获取角色 ID
     *
     * @param userId 用户ID
     * @return 角色ID集合
     */
    Set<Long> getRoleIdsByUserId(Long userId);

    /**
     * 保存用户角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID集合
     * @return true：成功，false：失败
     */
    boolean saveUserRoles(Long userId, Set<Long> roleIds);
}

