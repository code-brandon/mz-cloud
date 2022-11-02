package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.entity.SysUserPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
     * @param userPosts
     * @return
     */
    int insertUserPosts(@Param("userPosts") Set<SysUserPostEntity> userPosts);

    /**
     * 根据用户名获取岗位ID集合
     * @param userId
     * @return
     */
    List<Long> getPostIdsByUserId(@Param("userId") Long userId);

}
