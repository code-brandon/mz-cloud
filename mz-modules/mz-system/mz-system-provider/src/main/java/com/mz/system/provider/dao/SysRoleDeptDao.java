package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.entity.SysRoleDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 角色和部门关联表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
public interface SysRoleDeptDao extends BaseMapper<SysRoleDeptEntity> {

    /**
     * 根据角色ID获部门ID集合
     * @param roleId
     * @return
     */
    Set<Long> selectDeptIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 批量插入
     * @param roleDepts
     * @return
     */
    int insertRoleDepts(@Param("roleDepts") Set<SysRoleDeptEntity> roleDepts);
}
