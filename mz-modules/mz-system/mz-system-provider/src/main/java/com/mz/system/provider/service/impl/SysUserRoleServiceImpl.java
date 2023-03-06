package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.common.utils.MzUtils;
import com.mz.system.model.entity.SysUserRoleEntity;
import com.mz.system.model.vo.SysUserVo;
import com.mz.system.model.vo.req.SysRoleBindUserReqVo;
import com.mz.system.model.vo.search.SysUserSearchVo;
import com.mz.system.provider.dao.SysUserRoleDao;
import com.mz.system.provider.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

    @Override
    public PageUtils<SysUserVo> getUserPageByRoleId(Map<String, Object> params, SysUserSearchVo userSearchVo) {
        IPage<SysUserVo> page = baseMapper.selectUserPageByRoleId(
                new Query<SysUserVo>().getPage(params),
                userSearchVo
        );
        return new PageUtils<>(page);
    }

    @Override
    public PageUtils<SysUserVo> getNotThisRoleUserPage(Map<String, Object> params, SysUserSearchVo userSearchVo) {
        IPage<SysUserVo> page = baseMapper.selectNotThisRoleUserPage(
                new Query<SysUserVo>().getPage(params),
                userSearchVo
        );
        return new PageUtils<>(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleBindUser(SysRoleBindUserReqVo roleBindUserReqVo) {
        Long roleId = roleBindUserReqVo.getRoleId();
        Set<Long> userIds = roleBindUserReqVo.getUserIds();
        Set<SysUserRoleEntity> userRoles = userIds.parallelStream().map(userId -> new SysUserRoleEntity(userId, roleId)).collect(Collectors.toSet());
        return SqlHelper.retBool(baseMapper.insertUserRoles(userRoles));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByRoleIdAndUserIds(SysRoleBindUserReqVo roleBindUserReqVo) {
        return  SqlHelper.retBool(baseMapper.deleteByRoleIdAndUserIds(roleBindUserReqVo));
    }

    @Override
    public Set<Long> getRoleIdsByUserId(Long userId) {
        return baseMapper.selectRoleIdsByUserId(userId);
    }

    @Override
    public boolean saveUserRoles(Long userId, Set<Long> roleIds) {
        if (MzUtils.notEmpty(roleIds)) {
            Set<SysUserRoleEntity> userRoles = roleIds.stream().map(roleId -> new SysUserRoleEntity(userId, roleId)).collect(Collectors.toSet());
            return SqlHelper.retBool(baseMapper.insertUserRoles(userRoles));
        }
        return false;
    }

}