package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.dto.SysUserLoginLogDto;
import com.mz.system.model.entity.SysUserEntity;
import com.mz.system.model.vo.LoginBodyVo;
import com.mz.system.model.vo.SysUserVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.req.SysUserIdAndPasswdReqVo;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 分页查询用户
     * @param params 分页参数
     * @param userReqVo 分页条件
     * @return 分数据
     */
    PageUtils<SysUserVo> queryPage(Map<String, Object> params, SysUserVo userReqVo);

    /**
     * 按名称获取用户
     * @param userName 用户名称
     * @return 用户信息
     */
    SysUserEntity getUserByName(String userName);

    /**
     * 按用户名加载用户
     * @param userName 用户姓名
     * @return 用户信息
     */
    SysUserDto loadUserByUserName(String userName);

    /**
     * 按用户名和密码加载用户(登录暴漏接口)
     * @param loginBodyVo
     * @return 用户个人信息
     */
    SysUserDto loadUserByUserNameAndPassword(LoginBodyVo loginBodyVo);

    /**
     * 保存用户信息
     *
     * @param sysUserVo 用户Vo
     * @return true：成功，false：失败
     */

    @Transactional(rollbackFor = Exception.class)
    boolean saveUser(SysUserVo sysUserVo);

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVo getUserById(Long userId);

    /**
     * 根据用户ID更新用户信息
     * @param sysUserVo 用户Vo
     * @return true：成功，false：失败
     */

    @Transactional(rollbackFor = Exception.class)
    boolean updateUserById(SysUserVo sysUserVo);

    /**
     * 重置密码
     * @param userVo 用户Vo
     * @return true：成功，false：失败
     */
    boolean resetPasswd(SysUserIdAndPasswdReqVo userVo);

    /**
     * 根据ID集合删除用户
     * @param userIds 用户IDS
     * @return true：成功，false：失败
     */
    boolean removeUserByIds(List<Long> userIds);

    /**
     * 修改状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo);

    /**
     * 修改登录记录
     * @param sysUserLoginLogDto 登录记录Dto
     * @return true：成功，false：失败
     */
    boolean updateLoginLog(SysUserLoginLogDto sysUserLoginLogDto);
}

