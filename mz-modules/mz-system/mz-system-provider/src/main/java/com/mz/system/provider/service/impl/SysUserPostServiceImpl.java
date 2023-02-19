package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.mz.common.utils.MzUtils;
import com.mz.system.model.entity.SysUserPostEntity;
import com.mz.system.provider.dao.SysUserPostDao;
import com.mz.system.provider.service.SysUserPostService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service("sysUserPostService")
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostDao, SysUserPostEntity> implements SysUserPostService {

    public boolean saveUserPosts(Long userId,Set<Long> postIds){
        if (MzUtils.notEmpty(postIds)) {
            Set<SysUserPostEntity> userPosts = postIds.stream().map(postId -> new SysUserPostEntity(userId, postId)).collect(Collectors.toSet());
            return SqlHelper.retBool(baseMapper.insertUserPosts(userPosts));
        }
        return false;
    }

    @Override
    public Set<Long> getPostIdsByUserId(Long userId) {
        return baseMapper.selectPostIdsByUserId(userId);
    }

}