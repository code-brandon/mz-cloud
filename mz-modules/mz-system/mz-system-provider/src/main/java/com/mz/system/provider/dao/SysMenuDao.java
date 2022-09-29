package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.dto.SysMenuDto;
import com.mz.system.model.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    /**
     * 根据用户ID获取菜单权限
     * @param userId
     * @return
     */
    Set<String> getAuthorities(Long userId);

    /**
     * 当前用户所具有菜单
     *
     * @param userId
     * @return
     */
    List<SysMenuDto> getMenuByUserId(@Param("userId") Long userId, @Param("ifAdmin") Boolean ifAdmin);

}
