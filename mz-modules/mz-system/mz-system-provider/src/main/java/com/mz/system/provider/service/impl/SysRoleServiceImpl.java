package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysRoleDeptEntity;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.entity.SysRoleMenuEntity;
import com.mz.system.model.vo.req.SysRoleReqVo;
import com.mz.system.model.vo.res.SysRoleResVo;
import com.mz.system.provider.dao.SysRoleDao;
import com.mz.system.provider.dao.SysRoleDeptDao;
import com.mz.system.provider.dao.SysRoleMenuDao;
import com.mz.system.provider.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("sysRoleService")
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    private final SysRoleMenuDao sysRoleMenuDao;

    private final SysRoleDeptDao sysRoleDeptDao;

    @Override
    public PageUtils<SysRoleEntity> queryPage(Map<String, Object> params, SysRoleReqVo sysRoleReqVo) {

        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(sysRoleReqVo, sysRoleEntity);

        IPage<SysRoleEntity> page = baseMapper.getRolePage(
                new Query<SysRoleEntity>().getPage(params),
                sysRoleEntity
        );
        return new PageUtils<>(page);
    }

    /**
     * 保存角色信息
     *
     * @param sysRoleResVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(SysRoleResVo sysRoleResVo) {

        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(sysRoleResVo, sysRoleEntity);
        if (save(sysRoleEntity)) {
            Long roleId = sysRoleEntity.getRoleId();
            insertRoleMenuOrRoleDept(sysRoleResVo, roleId);
            return true;
        }
        return false;
    }

    @Override
    public SysRoleResVo getRoleById(Long roleId) {

        SysRoleEntity sysRoleEntity = baseMapper.selectById(roleId);
        SysRoleResVo sysRoleResVo = new SysRoleResVo();
        BeanUtils.copyProperties(sysRoleEntity, sysRoleResVo);

        List<Long> menuIds = sysRoleMenuDao.getMenuIdsByRoleId(roleId);
        List<Long> deptIds = sysRoleDeptDao.getDeptIdsByRoleId(roleId);

        sysRoleResVo.setMenuIds(menuIds);
        sysRoleResVo.setDeptIds(deptIds);

        return sysRoleResVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleById(SysRoleResVo sysRoleResVo) {

        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(sysRoleResVo, sysRoleEntity);

        if (updateById(sysRoleEntity)) {
            Long roleId = sysRoleEntity.getRoleId();
            // 删除角色的绑定关系
            sysRoleMenuDao.delete(Wrappers.<SysRoleMenuEntity>lambdaQuery().eq(SysRoleMenuEntity::getRoleId, roleId));
            sysRoleDeptDao.delete(Wrappers.<SysRoleDeptEntity>lambdaQuery().eq(SysRoleDeptEntity::getRoleId, roleId));

            // 重新添加绑定关系
            insertRoleMenuOrRoleDept(sysRoleResVo, roleId);

            return true;
        }
        return false;
    }

    /**
     * 批量删除 角色相关信息
     *
     * @param roleIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeRoleByIds(List<Long> roleIds) {

        if (removeByIds(roleIds)) {
            sysRoleMenuDao.delete(Wrappers.<SysRoleMenuEntity>lambdaQuery().in(SysRoleMenuEntity::getRoleId, roleIds));
            sysRoleDeptDao.delete(Wrappers.<SysRoleDeptEntity>lambdaQuery().in(SysRoleDeptEntity::getRoleId, roleIds));
            return true;
        }
        // 删除 失败回滚删除的数据
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return false;
    }

    private void insertRoleMenuOrRoleDept(SysRoleResVo sysRoleResVo, Long roleId) {
        List<Long> menuIds = sysRoleResVo.getMenuIds();
        if (!CollectionUtils.isEmpty(menuIds)) {
            Set<SysRoleMenuEntity> roleMenus = menuIds.stream().map(menuId -> new SysRoleMenuEntity(roleId, menuId)).collect(Collectors.toSet());
            sysRoleMenuDao.insertRoleMenus(roleMenus);
        }
        List<Long> deptIds = sysRoleResVo.getDeptIds();
        if (!CollectionUtils.isEmpty(deptIds)) {
            Set<SysRoleDeptEntity> roleDepts = deptIds.stream().map(deptId -> new SysRoleDeptEntity(roleId, deptId)).collect(Collectors.toSet());
            sysRoleDeptDao.insertRoleDepts(roleDepts);
        }
    }

}