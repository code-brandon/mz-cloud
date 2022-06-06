package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 用户信息表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 按用户名加载用户
     * @param username 用户名
     * @return
     */
    SysUserDto loadUserByUserName(String username);

    /**
     * 获取权限
     * @return
     */
    Set<String> getAuthorities(Long userId);
}
