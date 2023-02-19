package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysRoleDeptEntity;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.entity.SysRoleMenuEntity;
import com.mz.system.model.vo.SysRoleVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.search.SysRoleSearchVo;
import com.mz.system.provider.dao.SysRoleDao;
import com.mz.system.provider.service.SysRoleDeptService;
import com.mz.system.provider.service.SysRoleMenuService;
import com.mz.system.provider.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Service("sysRoleService")
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    private final SysRoleMenuService sysRoleMenuService;

    private final SysRoleDeptService sysRoleDeptService;

    @Override
    public PageUtils<SysRoleEntity> queryPage(Map<String, Object> params, SysRoleSearchVo roleSearchVo) {
        IPage<SysRoleEntity> page = super.page(
                new Query<SysRoleEntity>().getPage(params),
                Wrappers.<SysRoleEntity>lambdaQuery()
                        .eq(StringUtils.isNotEmpty(roleSearchVo.getRoleKey()),SysRoleEntity::getRoleKey, roleSearchVo.getRoleKey())
                        .eq(StringUtils.isNotEmpty(roleSearchVo.getStatus()),SysRoleEntity::getStatus, roleSearchVo.getStatus())
                        .like(StringUtils.isNotEmpty(roleSearchVo.getRoleName()),SysRoleEntity::getRoleName, roleSearchVo.getRoleName())
                        .orderBy(Boolean.TRUE,Boolean.TRUE,SysRoleEntity::getRoleSort, SysRoleEntity::getCreateTime)
        );

        return new PageUtils<>(page);
    }

    /**
     * 保存角色信息
     *
     * @param sysRoleVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(SysRoleVo sysRoleVo) {
        SysRoleEntity sysRoleEntity = BeanUtil.copyProperties(sysRoleVo, SysRoleEntity.class);
        if (save(sysRoleEntity)) {
            Long roleId = sysRoleEntity.getRoleId();
            insertRoleMenuOrRoleDept(sysRoleVo, roleId);
            return true;
        }
        return false;
    }

    /**
     * 修改状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return 修改结果
     */
    @Override
    public boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo) {
        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        sysRoleEntity.setRoleId(idAndStatusReqVo.getSysId());
        sysRoleEntity.setStatus(idAndStatusReqVo.getStatus());
        return updateById(sysRoleEntity);
    }

    @Override
    public SysRoleVo getRoleById(Long roleId) {

        SysRoleEntity sysRoleEntity = baseMapper.selectById(roleId);
        SysRoleVo sysRoleVo = BeanUtil.copyProperties(sysRoleEntity, SysRoleVo.class);

        Set<Long> menuIds = sysRoleMenuService.getMenuIdsByRoleId(roleId);
        Set<Long> deptIds = sysRoleDeptService.getDeptIdsByRoleId(roleId);

        sysRoleVo.setMenuIds(menuIds);
        sysRoleVo.setDeptIds(deptIds);

        return sysRoleVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleById(SysRoleVo sysRoleVo) {

        SysRoleEntity sysRoleEntity = BeanUtil.copyProperties(sysRoleVo, SysRoleEntity.class);

        if (updateById(sysRoleEntity)) {
            Long roleId = sysRoleEntity.getRoleId();
            // 删除角色的绑定关系
            sysRoleMenuService.remove(Wrappers.<SysRoleMenuEntity>lambdaQuery().eq(SysRoleMenuEntity::getRoleId, roleId));
            sysRoleDeptService.remove(Wrappers.<SysRoleDeptEntity>lambdaQuery().eq(SysRoleDeptEntity::getRoleId, roleId));

            // 重新添加绑定关系
            insertRoleMenuOrRoleDept(sysRoleVo, roleId);

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

        return removeByIds(roleIds);
    }

    private void insertRoleMenuOrRoleDept(SysRoleVo sysRoleVo, Long roleId) {
        Set<Long> menuIds = sysRoleVo.getMenuIds();
        sysRoleMenuService.saveRoleMenus(roleId, menuIds);
        Set<Long> deptIds = sysRoleVo.getDeptIds();
        sysRoleDeptService.saveRoleDepts(roleId, deptIds);
    }

}