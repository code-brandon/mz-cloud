package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mz.common.mybatis.annotation.MzDataAuth;
import com.mz.system.model.entity.SysUserRoleEntity;
import com.mz.system.model.vo.SysUserVo;
import com.mz.system.model.vo.req.SysRoleBindUserReqVo;
import com.mz.system.model.vo.search.SysUserSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     * @param userRoles 用户角色集合
     * @return 保存条数
     */
    int insertUserRoles(@Param("userRoles") Set<SysUserRoleEntity> userRoles);

    /**
     * 根据用ID获取角色ID集合
     *
     * @param userId 用户ID
     * @return 角色ID集合
     */
    Set<Long> selectRoleIdsByUserId(@Param("userId") Long userId);


    /**
     * 根据用ID获取角色ID字符串集合
     *
     * @param userId 用户ID
     * @return 角色ID字符串集合(逗号间隔)
     */
    String selectStrRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询用户分页数据
     *
     * @param page         分页参数
     * @param userSearchVo 分页条件
     * @return 分页数据
     */
    @MzDataAuth
    IPage<SysUserVo> selectUserPageByRoleId(@Param("page") IPage<SysUserVo> page, @Param("userByRole") SysUserSearchVo userSearchVo);

    /**
     * 根据角色ID查询不是此角色用户分页数据
     *
     * @param page         分页参数
     * @param userSearchVo 分页条件
     * @return 分页数据
     */
    @MzDataAuth
    IPage<SysUserVo> selectNotThisRoleUserPage(@Param("page") IPage<SysUserVo> page, @Param("userByRole") SysUserSearchVo userSearchVo);

    /**
     * 按角色ID和用户ID删除
     *
     * @param roleBindUserReqVo 角色绑定用户请求 Vo
     * @return 删除条数
     */
    int deleteByRoleIdAndUserIds(@Param("userByRole") SysRoleBindUserReqVo roleBindUserReqVo);
}
