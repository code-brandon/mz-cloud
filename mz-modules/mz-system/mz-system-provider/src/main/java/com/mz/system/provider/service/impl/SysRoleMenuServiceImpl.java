package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.mz.common.utils.MzUtils;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.entity.SysRoleMenuEntity;
import com.mz.system.model.vo.req.SysRoleMenuReqVo;
import com.mz.system.provider.dao.SysRoleDao;
import com.mz.system.provider.dao.SysRoleMenuDao;
import com.mz.system.provider.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;


@Service("sysRoleMenuService")
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

    private final SysRoleDao sysRoleDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleMenu(SysRoleMenuReqVo sysRoleMenuReqVo) {
        SysRoleEntity sysRoleEntity = BeanUtil.copyProperties(sysRoleMenuReqVo, SysRoleEntity.class);
        if (sysRoleDao.insert(sysRoleEntity) > 0) {
            Long roleId = sysRoleEntity.getRoleId();
            saveRoleMenus(roleId, sysRoleMenuReqVo.getMenuIds());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleMenuById(SysRoleMenuReqVo sysRoleMenuReqVo) {
        SysRoleEntity sysRoleEntity = BeanUtil.copyProperties(sysRoleMenuReqVo, SysRoleEntity.class);
        if (sysRoleDao.updateById(sysRoleEntity) > 0) {
            Long roleId = sysRoleEntity.getRoleId();
            super.remove(Wrappers.<SysRoleMenuEntity>lambdaQuery().eq(SysRoleMenuEntity::getRoleId, roleId));
            saveRoleMenus(roleId, sysRoleMenuReqVo.getMenuIds());
            return true;
        }
        return false;
    }

    /**
     * 按角色 ID 获取菜单 ID
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    public Set<Long> getMenuIdsByRoleId(Long roleId) {
        return baseMapper.selectMenuIdsByRoleId(roleId);
    }

    /**
     * 保存角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID
     * @return
     */
    @Override
    public boolean saveRoleMenus(Long roleId, Set<Long> menuIds) {
        if (MzUtils.notEmpty(menuIds)) {
            Set<SysRoleMenuEntity> roleMenus = menuIds.stream().map(menuId -> new SysRoleMenuEntity(roleId, menuId)).collect(Collectors.toSet());
            return SqlHelper.retBool(baseMapper.insertRoleMenus(roleMenus));
        }
        return false;
    }

}