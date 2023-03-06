package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 角色和菜单关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

    /**
     * 根据角色Id获取菜单ID集合
     *
     * @param roleId 角色ID
     * @return 角色ID集合
     */
    Set<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 批量插入 角色和菜单关联
     *
     * @param roleMenus 角色菜单集合
     * @return 插入条数
     */
    int insertRoleMenus(@Param("roleMenus") Set<SysRoleMenuEntity> roleMenus);
}
