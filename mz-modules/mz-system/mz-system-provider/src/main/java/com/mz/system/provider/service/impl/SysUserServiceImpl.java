package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.core.exception.MzBaseException;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.entity.SysUserEntity;
import com.mz.system.model.vo.LoginBodyVo;
import com.mz.system.provider.dao.SysUserDao;
import com.mz.system.provider.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
        );
        return new PageUtils(page);
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

}