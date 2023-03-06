package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.system.model.entity.SysRoleMenuEntity;
import com.mz.system.model.vo.req.SysRoleMenuReqVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 角色和菜单关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

    /**
     * 保存角色菜单
     *
     * @param sysRoleMenuReqVo 实体类
     * @return true：成功，false：失败
     */

    @Transactional(rollbackFor = Exception.class)
    boolean saveRoleMenu(SysRoleMenuReqVo sysRoleMenuReqVo);

    /**
     * 修改角色菜单
     *
     * @param sysRoleMenuReqVo 实体类
     * @return true：成功，false：失败
     */

    @Transactional(rollbackFor = Exception.class)
    boolean updateRoleMenuById(SysRoleMenuReqVo sysRoleMenuReqVo);

    /**
     * 按角色 ID 获取菜单 ID
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    Set<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 保存角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID
     * @return true：成功，false：失败
     */
    boolean saveRoleMenus(Long roleId, Set<Long> menuIds);
}

