package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysUserRoleEntity;
import com.mz.system.model.vo.req.SysRoleBindUserReqVo;
import com.mz.system.model.vo.req.SysUserByRoleIdReqVo;
import com.mz.system.model.vo.res.SysUserResVo;
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
    public PageUtils<SysUserRoleEntity> queryPage(Map<String, Object> params) {
        IPage<SysUserRoleEntity> page = this.page(
                new Query<SysUserRoleEntity>().getPage(params),
                new QueryWrapper<SysUserRoleEntity>()
        );
        return new PageUtils<>(page);
    }

    /**
     * 根据角色ID查询用户分页数据
     *
     * @param params            分页数据
     * @param userByRoleIdResVo 分页条件
     * @return
     */
    @Override
    public PageUtils<SysUserResVo> getUserPageByRoleId(Map<String, Object> params, SysUserByRoleIdReqVo userByRoleIdResVo) {
        IPage<SysUserResVo> page = baseMapper.getUserPageByRoleId(
                new Query<SysUserResVo>().getPage(params),
                userByRoleIdResVo
        );
        return new PageUtils<>(page);
    }

    /**
     * 根据角色ID查询不是此角色用户分页数据
     *
     * @param params            分页数据
     * @param userByRoleIdResVo 分页条件
     * @return
     */
    @Override
    public PageUtils<SysUserResVo> getNotThisRoleUserPage(Map<String, Object> params, SysUserByRoleIdReqVo userByRoleIdResVo) {
        IPage<SysUserResVo> page = baseMapper.getNotThisRoleUserPage(
                new Query<SysUserResVo>().getPage(params),
                userByRoleIdResVo
        );
        return new PageUtils<>(page);
    }

    /**
     * 保存角色绑定用户的关系
     *
     * @param roleBindUserReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleBindUser(SysRoleBindUserReqVo roleBindUserReqVo) {
        Long roleId = roleBindUserReqVo.getRoleId();
        Set<Long> userIds = roleBindUserReqVo.getUserIds();
        Set<SysUserRoleEntity> userRoles = userIds.parallelStream().map(userId -> new SysUserRoleEntity(userId, roleId)).collect(Collectors.toSet());
        return baseMapper.insertUserRoles(userRoles) > 0;
    }

    /**
     * 按角色ID和用户ID删除
     *
     * @param roleBindUserReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByRoleIdAndUserIds(SysRoleBindUserReqVo roleBindUserReqVo) {
        return baseMapper.deleteByRoleIdAndUserIds(roleBindUserReqVo) > 0;
    }

}