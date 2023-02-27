package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mz.common.mybatis.annotation.MzDataAuth;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.dto.SysUserLoginLogDto;
import com.mz.system.model.entity.SysUserEntity;
import com.mz.system.model.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户信息表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
@MzDataAuth
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 按用户名加载用户
     *
     * @param username 用户名
     * @return
     */
    @MzDataAuth(ignore = true)
    SysUserDto loadUserByUserName(String username);

    /**
     * 用户列表分页查询
     * @param page 分页条件
     * @param userReqVo 用户Vo
     * @return 分页数据
     */
    IPage<SysUserVo> selectPageUser(IPage<SysUserVo> page, @Param("userVo") SysUserVo userReqVo);

    int updateLoginLog(@Param("sysUserLog") SysUserLoginLogDto userLoginLogDto);
}
