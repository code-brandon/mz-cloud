package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysRoleDeptEntity;
import com.mz.system.model.vo.req.SysRoleDeptReqVo;

import java.util.Map;
import java.util.Set;

/**
 * 角色和部门关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysRoleDeptService extends IService<SysRoleDeptEntity> {

    PageUtils<SysRoleDeptEntity> queryPage(Map<String, Object> params);

    boolean saveRoleDept(SysRoleDeptReqVo sysRoleDeptReqVo);

    /**
     * 按角色 ID 获取部门 ID
     * @param roleId
     * @return
     */
    Set<Long> getDeptIdsByRoleId(Long roleId);

    /**
     * 保存角色部门
     * @param roleId 角色ID
     * @param deptIds 部门ID集合
     * @return
     */
    boolean saveRoleDepts(Long roleId,Set<Long> deptIds);
}

