package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.vo.req.SysRoleReqVo;
import com.mz.system.model.vo.res.SysRoleResVo;

import java.util.List;
import java.util.Map;

/**
 * 角色信息表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    PageUtils<SysRoleEntity> queryPage(Map<String, Object> params, SysRoleReqVo sysRoleReqVo);

    /**
     * 根据ID获取角色信息
     * @param roleId
     * @return
     */
    SysRoleResVo getRoleById(Long roleId);

    /**
     * 根据ID更新角色信息
     * @param sysRoleResVo
     * @return
     */
    boolean updateRoleById(SysRoleResVo sysRoleResVo);

    /**
     * 批量删除 角色相关信息
     * @param roleIds
     * @return
     */
    boolean removeRoleByIds(List<Long> roleIds);

    /**
     * 保存角色信息
     * @param sysRoleResVo
     * @return
     */
    boolean saveRole(SysRoleResVo sysRoleResVo);
}

