package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.vo.SysRoleVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.search.SysRoleSearchVo;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 分页查询角色信息
     * @param params 分页参数
     * @param roleSearchVo 查询参数
     * @return 分页数据
     */
    PageUtils<SysRoleEntity> queryPage(Map<String, Object> params, SysRoleSearchVo roleSearchVo);

    /**
     * 根据ID获取角色信息
     * @param roleId 角色ID
     * @return 角色信息
     */
    SysRoleVo getRoleById(Long roleId);

    /**
     * 根据ID更新角色信息
     * @param sysRoleVo 实体类
     * @return true：成功，false：失败
     */

    @Transactional(rollbackFor = Exception.class)
    boolean updateRoleById(SysRoleVo sysRoleVo);

    /**
     * 批量删除 角色相关信息
     * @param roleIds 角色ID集合
     * @return true：成功，false：失败
     */

    @Transactional(rollbackFor = Exception.class)
    boolean removeRoleByIds(List<Long> roleIds);

    /**
     * 保存角色信息
     * @param sysRoleVo
     * @return true：成功，false：失败
     */

    @Transactional(rollbackFor = Exception.class)
    boolean saveRole(SysRoleVo sysRoleVo);

    /**
     * 修改角色状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo);
}

