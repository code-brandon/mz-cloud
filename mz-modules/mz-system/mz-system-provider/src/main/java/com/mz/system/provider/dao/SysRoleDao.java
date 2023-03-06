package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 角色信息表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    /**
     * 根据用户ID获取角色列表
     * @param userId 用户ID
     * @return 角色权限列表
     */
    Set<String> getRolePermissions(Long userId);

    /**
     * 根据用户ID和角色Key判断是否是当前角色
     * @param userId 用户ID
     * @param roleKey 角色KEy
     * @return true：是，false：否
     */
    boolean isRole(Long userId, String roleKey);
}
