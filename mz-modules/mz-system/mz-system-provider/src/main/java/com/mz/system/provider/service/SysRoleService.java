package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.vo.SysRoleVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.search.SysRoleSearchVo;

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

    PageUtils<SysRoleEntity> queryPage(Map<String, Object> params, SysRoleSearchVo roleSearchVo);

    /**
     * 根据ID获取角色信息
     * @param roleId
     * @return
     */
    SysRoleVo getRoleById(Long roleId);

    /**
     * 根据ID更新角色信息
     * @param sysRoleVo
     * @return
     */
    boolean updateRoleById(SysRoleVo sysRoleVo);

    /**
     * 批量删除 角色相关信息
     * @param roleIds
     * @return
     */
    boolean removeRoleByIds(List<Long> roleIds);

    /**
     * 保存角色信息
     * @param sysRoleVo
     * @return
     */
    boolean saveRole(SysRoleVo sysRoleVo);

    /**
     * 修改状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return 修改结果
     */
    boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo);
}

