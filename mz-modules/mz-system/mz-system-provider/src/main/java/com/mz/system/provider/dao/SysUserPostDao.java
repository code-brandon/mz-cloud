package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.entity.SysUserPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 用户与岗位关联表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
public interface SysUserPostDao extends BaseMapper<SysUserPostEntity> {

    /**
     * 批量插入 用户与岗位关联
     * @param userPosts 用户岗位关联集合
     * @return 插入条数
     */
    int insertUserPosts(@Param("userPosts") Set<SysUserPostEntity> userPosts);

    /**
     * 根据用户名获取岗位ID集合
     * @param userId 用户ID
     * @return 岗位ID集合
     */
    Set<Long> selectPostIdsByUserId(@Param("userId") Long userId);

}
