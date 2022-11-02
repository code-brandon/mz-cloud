package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.core.exception.MzBaseException;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.entity.SysUserEntity;
import com.mz.system.model.entity.SysUserPostEntity;
import com.mz.system.model.entity.SysUserRoleEntity;
import com.mz.system.model.vo.LoginBodyVo;
import com.mz.system.model.vo.req.SysUserIdAndPasswdReqVo;
import com.mz.system.model.vo.res.SysUserResVo;
import com.mz.system.provider.dao.SysUserDao;
import com.mz.system.provider.dao.SysUserPostDao;
import com.mz.system.provider.dao.SysUserRoleDao;
import com.mz.system.provider.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service("sysUserService")
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    private final SysUserRoleDao sysUserRoleDao;

    private final SysUserPostDao sysUserPostDao;

    @Override
    public PageUtils<SysUserResVo> queryPage(Map<String, Object> params, SysUserResVo userReqVo) {
        IPage<SysUserResVo> page = baseMapper.getUserPage(
                new Query<SysUserResVo>().getPage(params),
                userReqVo
        );
        return new PageUtils<>(page);
    }

    /**
     * 按名称获取用户
     *
     * @param userName
     * @return
     */
    @Override
    public SysUserEntity getUserByName(String userName) {
        return baseMapper.selectOne(new QueryWrapper<SysUserEntity>().eq("user_name", userName));
    }

    @Override
    public SysUserDto loadUserByUserName(String userName) {
        return baseMapper.loadUserByUserName(userName);
    }

    @Override
    public SysUserDto loadUserByUserNameAndPassword(LoginBodyVo loginBodyVo) {
        SysUserDto sysUserDto = loadUserByUserName(loginBodyVo.getUsername());
        if (Objects.nonNull(sysUserDto)) {
            boolean matches = passwordEncoder.matches(loginBodyVo.getPassword(), sysUserDto.getPassword());
            if (!matches) {
                throw new MzBaseException("密码校验失败！");
            }
        }
        return sysUserDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(SysUserResVo sysUserResVo) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtils.copyProperties(sysUserResVo, sysUserEntity);

        String encodePassword = passwordEncoder.encode(sysUserEntity.getPassword());
        sysUserEntity.setPassword(encodePassword);

        if (save(sysUserEntity)) {
            Long userId = sysUserEntity.getUserId();
            insertUserRolesOrUserPosts(sysUserResVo, userId);
            return true;
        }
        return false;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId
     * @return
     */
    @Override
    public SysUserResVo getUserById(Long userId) {

        SysUserEntity sysUserEntity = getById(userId);
        SysUserResVo sysUserResVo = new SysUserResVo();
        BeanUtils.copyProperties(sysUserEntity, sysUserResVo);

        List<Long> postIds = sysUserPostDao.getPostIdsByUserId(userId);
        List<Long> roleIds = sysUserRoleDao.getRoleIdsByUserId(userId);

        sysUserResVo.setPostIds(postIds);
        sysUserResVo.setRoleIds(roleIds);

        return sysUserResVo;
    }

    /**
     * 根据ID更新用户信息
     *
     * @param sysUserResVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserById(SysUserResVo sysUserResVo) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtils.copyProperties(sysUserResVo, sysUserEntity);

        sysUserEntity.setPassword(null);

        if (updateById(sysUserEntity)) {

            Long userId = sysUserEntity.getUserId();
            // 删除用户的绑定关系
            sysUserPostDao.delete(Wrappers.<SysUserPostEntity>lambdaQuery().eq(SysUserPostEntity::getUserId, userId));
            sysUserRoleDao.delete(Wrappers.<SysUserRoleEntity>lambdaQuery().eq(SysUserRoleEntity::getUserId, userId));

            // 重新添加绑定关系
            insertUserRolesOrUserPosts(sysUserResVo, userId);

            return true;
        }
        return false;
    }

    @Override
    public boolean resetPasswd(SysUserIdAndPasswdReqVo userVo) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        String encodePassword = passwordEncoder.encode(userVo.getPassword());
        sysUserEntity.setUserId(userVo.getUserId());
        sysUserEntity.setPassword(encodePassword);
        return baseMapper.updateById(sysUserEntity) > 0;
    }

    /**
     * 根据ID集合删除用户
     *
     * @param userIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUserByIds(List<Long> userIds) {
        if (removeByIds(userIds)) {

            sysUserPostDao.delete(Wrappers.<SysUserPostEntity>lambdaQuery().in(SysUserPostEntity::getUserId, userIds));
            sysUserRoleDao.delete(Wrappers.<SysUserRoleEntity>lambdaQuery().in(SysUserRoleEntity::getUserId, userIds));
            return true;
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return false;
    }

    /**
     * 添加用户绑定关系
     *
     * @param sysUserResVo
     * @param userId
     */
    private void insertUserRolesOrUserPosts(SysUserResVo sysUserResVo, Long userId) {
        List<Long> roleIds = sysUserResVo.getRoleIds();
        if (!CollectionUtils.isEmpty(roleIds)) {
            Set<SysUserRoleEntity> userRoles = roleIds.stream().map(roleId -> new SysUserRoleEntity(userId, roleId)).collect(Collectors.toSet());
            sysUserRoleDao.insertUserRoles(userRoles);
        }
        List<Long> postIds = sysUserResVo.getPostIds();
        if (!CollectionUtils.isEmpty(postIds)) {
            Set<SysUserPostEntity> userPosts = postIds.stream().map(postId -> new SysUserPostEntity(userId, postId)).collect(Collectors.toSet());
            sysUserPostDao.insertUserPosts(userPosts);
        }
    }

}