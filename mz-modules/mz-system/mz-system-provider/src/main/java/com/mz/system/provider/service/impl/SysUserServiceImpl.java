package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.mz.common.core.exception.MzException;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.dto.SysUserLoginLogDto;
import com.mz.system.model.entity.SysUserEntity;
import com.mz.system.model.entity.SysUserPostEntity;
import com.mz.system.model.entity.SysUserRoleEntity;
import com.mz.system.model.vo.LoginBodyVo;
import com.mz.system.model.vo.SysUserVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.req.SysUserIdAndPasswdReqVo;
import com.mz.system.provider.dao.SysUserDao;
import com.mz.system.provider.service.SysUserPostService;
import com.mz.system.provider.service.SysUserRoleService;
import com.mz.system.provider.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


@Service("sysUserService")
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    private final SysUserRoleService sysUserRoleService;

    private final SysUserPostService sysUserPostService;

    @Override
    public PageUtils<SysUserVo> queryPage(Map<String, Object> params, SysUserVo userReqVo) {
        IPage<SysUserVo> page = baseMapper.selectPageUser(
                new Query<SysUserVo>().getPage(params),
                userReqVo
        );
        return new PageUtils<>(page);
    }

    @Override
    public SysUserEntity getUserByName(String userName) {
        SysUserEntity sysUserEntity = super.getOne(new QueryWrapper<SysUserEntity>().eq("username", userName));
        return BeanUtil.copyProperties(sysUserEntity, SysUserVo.class);
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
                throw new MzException("密码校验失败！");
            }
        }
        return sysUserDto;
    }

    @Override
    public boolean saveUser(SysUserVo sysUserVo) {
        SysUserEntity sysUserEntity = BeanUtil.copyProperties(sysUserVo, SysUserEntity.class);
        String encodePassword = passwordEncoder.encode(sysUserEntity.getPassword());
        sysUserEntity.setPassword(encodePassword);

        if (save(sysUserEntity)) {
            Long userId = sysUserEntity.getUserId();
            insertUserRolesOrUserPosts(sysUserVo, userId);
            return true;
        }
        return false;
    }

    @Override
    public SysUserVo getUserById(Long userId) {

        SysUserEntity sysUserEntity = getById(userId);
        SysUserVo sysUserVo = BeanUtil.copyProperties(sysUserEntity, SysUserVo.class);
        Set<Long> postIds = sysUserPostService.getPostIdsByUserId(userId);
        Set<Long> roleIds = sysUserRoleService.getRoleIdsByUserId(userId);

        sysUserVo.setPostIds(postIds);
        sysUserVo.setRoleIds(roleIds);

        return sysUserVo;
    }

    @Override
    public boolean updateUserById(SysUserVo sysUserVo) {
        SysUserEntity sysUserEntity = BeanUtil.copyProperties(sysUserVo, SysUserEntity.class, "password","loginIp","loginDate");
        if (updateById(sysUserEntity)) {

            Long userId = sysUserEntity.getUserId();
            // 删除用户的绑定关系
            sysUserPostService.remove(Wrappers.<SysUserPostEntity>lambdaQuery().eq(SysUserPostEntity::getUserId, userId));
            sysUserRoleService.remove(Wrappers.<SysUserRoleEntity>lambdaQuery().eq(SysUserRoleEntity::getUserId, userId));

            // 重新添加绑定关系
            insertUserRolesOrUserPosts(sysUserVo, userId);

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
        return super.updateById(sysUserEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUserByIds(List<Long> userIds) {
        return removeByIds(userIds);
    }

    @Override
    public boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity .setUserId(idAndStatusReqVo.getSysId());
        sysUserEntity.setStatus(idAndStatusReqVo.getStatus());
        return super.updateById(sysUserEntity);
    }

    @Override
    public boolean updateLoginLog(SysUserLoginLogDto sysUserLoginLogDto) {
        return SqlHelper.retBool(baseMapper.updateLoginLog(sysUserLoginLogDto));
    }

    /**
     * 添加用户绑定关系
     *
     * @param sysUserVo 用户Vo
     * @param userId 用户ID
     */
    private void insertUserRolesOrUserPosts(SysUserVo sysUserVo, Long userId) {
        Set<Long> roleIds = sysUserVo.getRoleIds();
        sysUserRoleService.saveUserRoles(userId,roleIds);
        Set<Long> postIds = sysUserVo.getPostIds();
        sysUserPostService.saveUserPosts(userId,postIds);
    }

}