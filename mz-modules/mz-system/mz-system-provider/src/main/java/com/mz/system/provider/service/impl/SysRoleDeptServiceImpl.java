package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.common.utils.MzUtils;
import com.mz.system.model.entity.SysRoleDeptEntity;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.vo.req.SysRoleDeptReqVo;
import com.mz.system.provider.dao.SysRoleDao;
import com.mz.system.provider.dao.SysRoleDeptDao;
import com.mz.system.provider.service.SysRoleDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("sysRoleDeptService")
@RequiredArgsConstructor
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDeptEntity> implements SysRoleDeptService {

    private final SysRoleDao sysRoleDao;

    @Override
    public PageUtils<SysRoleDeptEntity> queryPage(Map<String, Object> params) {
        IPage<SysRoleDeptEntity> page = super.page(
                new Query<SysRoleDeptEntity>().getPage(params),
                new QueryWrapper<SysRoleDeptEntity>()
        );
        return new PageUtils<>(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleDept(SysRoleDeptReqVo sysRoleDeptReqVo) {
        SysRoleEntity sysRoleEntity = BeanUtil.copyProperties(sysRoleDeptReqVo, SysRoleEntity.class);
        if (sysRoleDao.updateById(sysRoleEntity) > 0) {
            Long roleId = sysRoleEntity.getRoleId();
            super.removeById(roleId);
            Set<Long> deptIds = sysRoleDeptReqVo.getDeptIds();
            saveRoleDepts(roleId, deptIds);
            return true;
        }
        return false;
    }


    /**
     * 按角色 ID 获取部门 ID
     *
     * @param roleId
     * @return
     */
    @Override
    public Set<Long> getDeptIdsByRoleId(Long roleId) {
        return baseMapper.selectDeptIdsByRoleId(roleId);
    }

    /**
     * 保存角色部门
     *
     * @param roleId  角色ID
     * @param deptIds 部门ID集合
     * @return
     */
    @Override
    public boolean saveRoleDepts(Long roleId, Set<Long> deptIds) {
        if (MzUtils.notEmpty(deptIds)) {
            Set<SysRoleDeptEntity> roleDepts = deptIds.stream().map(deptId -> new SysRoleDeptEntity(roleId, deptId)).collect(Collectors.toSet());
            return SqlHelper.retBool(baseMapper.insertRoleDepts(roleDepts));
        }
        return false;
    }
}