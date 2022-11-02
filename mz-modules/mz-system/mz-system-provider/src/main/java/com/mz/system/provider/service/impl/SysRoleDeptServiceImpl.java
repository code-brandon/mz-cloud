package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysRoleDeptEntity;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.vo.req.SysRoleDeptReqVo;
import com.mz.system.provider.dao.SysRoleDao;
import com.mz.system.provider.dao.SysRoleDeptDao;
import com.mz.system.provider.service.SysRoleDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("sysRoleDeptService")
@RequiredArgsConstructor
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDeptEntity> implements SysRoleDeptService {

    private final SysRoleDao sysRoleDao;

    @Override
    public PageUtils<SysRoleDeptEntity> queryPage(Map<String, Object> params) {
        IPage<SysRoleDeptEntity> page = this.page(
                new Query<SysRoleDeptEntity>().getPage(params),
                new QueryWrapper<SysRoleDeptEntity>()
        );
        return new PageUtils<>(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleDept(SysRoleDeptReqVo sysRoleDeptReqVo) {

        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(sysRoleDeptReqVo, sysRoleEntity);
        if (sysRoleDao.updateById(sysRoleEntity) > 0) {
            Long roleId = sysRoleEntity.getRoleId();
            baseMapper.deleteById(roleId);
            List<Long> deptIds = sysRoleDeptReqVo.getDeptIds();
            if (!CollectionUtils.isEmpty(deptIds)) {
                Set<SysRoleDeptEntity> roleDepts = deptIds.stream().map(deptId -> new SysRoleDeptEntity(roleId, deptId)).collect(Collectors.toSet());
                baseMapper.insertRoleDepts(roleDepts);
            }
            return true;
        }
        return false;
    }

}