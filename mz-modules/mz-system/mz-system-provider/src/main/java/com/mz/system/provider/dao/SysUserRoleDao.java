package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mz.system.model.entity.SysUserRoleEntity;
import com.mz.system.model.vo.req.SysRoleBindUserReqVo;
import com.mz.system.model.vo.req.SysUserByRoleIdReqVo;
import com.mz.system.model.vo.res.SysUserResVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 用户和角色关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

    /**
     * 批量插入 用户和角色关联
     *
     * @param userRoles
     * @return
     */
    int insertUserRoles(@Param("userRoles") Set<SysUserRoleEntity> userRoles);

    /**
     * 根据用户名获取角色ID集合
     *
     * @param userId
     * @return
     */
    List<Long> getRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询用户分页数据
     *
     * @param page
     * @param userByRoleIdResVo
     * @return
     */
    IPage<SysUserResVo> getUserPageByRoleId(@Param("page") IPage<SysUserResVo> page, @Param("userByRole") SysUserByRoleIdReqVo userByRoleIdResVo);

    /**
     * 根据角色ID查询不是此角色用户分页数据
     *
     * @param page
     * @param userByRoleIdResVo
     * @return
     */
    IPage<SysUserResVo> getNotThisRoleUserPage(@Param("page") IPage<SysUserResVo> page, @Param("userByRole") SysUserByRoleIdReqVo userByRoleIdResVo);

    /**
     * 按角色ID和用户ID删除
     *
     * @param roleBindUserReqVo
     * @return
     */
    int deleteByRoleIdAndUserIds(@Param("userByRole") SysRoleBindUserReqVo roleBindUserReqVo);
}
