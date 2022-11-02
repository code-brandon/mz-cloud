package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.entity.SysUserEntity;
import com.mz.system.model.vo.LoginBodyVo;
import com.mz.system.model.vo.req.SysUserIdAndPasswdReqVo;
import com.mz.system.model.vo.res.SysUserResVo;

import java.util.List;
import java.util.Map;

/**
 * 用户信息表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysUserService extends IService<SysUserEntity> {

    PageUtils<SysUserResVo> queryPage(Map<String, Object> params, SysUserResVo userReqVo);

    /**
     * 按名称获取用户
     * @param userName
     * @return
     */
    SysUserEntity getUserByName(String userName);

    /**
     * 按用户名加载用户
     * @param userName
     * @return
     */
    SysUserDto loadUserByUserName(String userName);

    /**
     * 按用户名和密码加载用户(登录暴漏接口)
     * @param loginBodyVo
     * @return
     */
    SysUserDto loadUserByUserNameAndPassword(LoginBodyVo loginBodyVo);

    /**
     * 保存用户信息
     *
     * @param sysUserResVo
     * @return
     */
    boolean saveUser(SysUserResVo sysUserResVo);

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    SysUserResVo getUserById(Long userId);

    /**
     * 根据用户ID更新用户信息
     * @param sysUserResVo
     * @return
     */
    boolean updateUserById(SysUserResVo sysUserResVo);

    /**
     * 重置密码
     * @param userVo
     * @return
     */
    boolean resetPasswd(SysUserIdAndPasswdReqVo userVo);

    /**
     * 根据ID集合删除用户
     * @param userIds
     * @return
     */
    boolean removeUserByIds(List<Long> userIds);
}

