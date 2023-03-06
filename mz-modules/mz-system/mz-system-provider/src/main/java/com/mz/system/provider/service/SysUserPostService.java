package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.system.model.entity.SysUserPostEntity;

import java.util.Set;

/**
 * 用户与岗位关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysUserPostService extends IService<SysUserPostEntity> {

    /**
     * 保存用户岗位
     * @param userId 用户ID
     * @param postIds 岗位ID
     * @return true：成功，false：失败
     */
    boolean saveUserPosts(Long userId, Set<Long> postIds);

    /**
     * 按用户 ID 获取岗位ID
     * @param userId 用户ID
     * @return true：成功，false：失败
     */
    Set<Long> getPostIdsByUserId(Long userId);
}

