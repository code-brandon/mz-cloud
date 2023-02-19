package com.mz.system.provider.service;

import cn.hutool.core.lang.tree.Tree;
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

    List<SysDeptEntity> queryList(SysDeptReqVo sysDeptReqVo);

    /**
     * 获取部门树列表
     * @return
     */
    List<Tree<Long>> getDeptTree();

    /**
     * 获取机构列表树
     *
     * @return
     */
    List<SysDeptTree> getDeptListTree(SysDeptReqVo sysDeptReqVo);

    /**
     * 保存部门信息
     *
     * @param sysDeptReqVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveDept(SysDeptReqVo sysDeptReqVo);

    /**
     * 根据DeptId获取祖级列表
     * @param deptId
     * @return
     */
    String getAncestorsByDeptId(Long deptId);

    /**
     * 根据DeptId修改数据
     * @param sysDeptReqVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updateDeptById(SysDeptReqVo sysDeptReqVo);
}

