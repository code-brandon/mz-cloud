package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.system.model.entity.SysDeptEntity;
import com.mz.system.model.vo.SysDeptTree;
import com.mz.system.model.vo.req.SysDeptReqVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysDeptService extends IService<SysDeptEntity> {

    /**
     * 查询所有部门
     *
     * @param sysDeptReqVo 查询条件
     * @return 部门列表
     */
    List<SysDeptEntity> queryList(SysDeptReqVo sysDeptReqVo);

    /**
     * 获取部门树列表
     *
     * @return 部门树
     */
    List<SysDeptTree> getDeptTree();

    /**
     * 获取部门列表树
     *
     * @param sysDeptReqVo 实体类
     * @return 部门列表树
     */
    List<SysDeptTree> getDeptListTree(SysDeptReqVo sysDeptReqVo);

    /**
     * 保存部门
     *
     * @param sysDeptReqVo 实体对象
     * @return true：成功，false：失败
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveDept(SysDeptReqVo sysDeptReqVo);

    /**
     * 根据部门Id获取祖级列表
     * @param deptId 部门ID
     * @return 逗号间隔数据
     */
    String getAncestorsByDeptId(Long deptId);

    /**
     * 根据岗位ID修改数据
     * @param sysDeptReqVo 实体类
     * @return true：成功，false：失败
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updateDeptById(SysDeptReqVo sysDeptReqVo);
}

