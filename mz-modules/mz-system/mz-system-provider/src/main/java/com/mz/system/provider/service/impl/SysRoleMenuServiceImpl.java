package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.entity.SysRoleMenuEntity;
import com.mz.system.model.vo.req.SysRoleMenuReqVo;
import com.mz.system.provider.dao.SysRoleDao;
import com.mz.system.provider.dao.SysRoleMenuDao;
import com.mz.system.provider.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("sysRoleMenuService")
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

    private final SysRoleDao sysRoleDao;

    @Override
    public PageUtils<SysRoleMenuEntity> queryPage(Map<String, Object> params) {
        IPage<SysRoleMenuEntity> page = this.page(
                new Query<SysRoleMenuEntity>().getPage(params),
                new QueryWrapper<SysRoleMenuEntity>()
        );
        return new PageUtils<>(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleMenu(SysRoleMenuReqVo sysRoleMenuReqVo) {

        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(sysRoleMenuReqVo, sysRoleEntity);
        if (sysRoleDao.insert(sysRoleEntity) > 0) {
            Long roleId = sysRoleEntity.getRoleId();
            insertRoleMenu(sysRoleMenuReqVo, roleId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleMenuById(SysRoleMenuReqVo sysRoleMenuReqVo) {
        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(sysRoleMenuReqVo, sysRoleEntity);
        if (sysRoleDao.updateById(sysRoleEntity) > 0) {
            Long roleId = sysRoleEntity.getRoleId();
            baseMapper.delete(Wrappers.<SysRoleMenuEntity>lambdaQuery().eq(SysRoleMenuEntity::getRoleId, roleId));
            insertRoleMenu(sysRoleMenuReqVo, roleId);
            return true;
        }
        return false;
    }

    private void insertRoleMenu(SysRoleMenuReqVo sysRoleMenuReqVo, Long roleId) {
        Set<Long> menuIds = sysRoleMenuReqVo.getMenuIds();
        if (!CollectionUtils.isEmpty(menuIds)) {
            Set<SysRoleMenuEntity> roleMenus = menuIds.stream().map(menuId -> new SysRoleMenuEntity(roleId, menuId)).collect(Collectors.toSet());
            baseMapper.insertRoleMenus(roleMenus);
        }
    }

}