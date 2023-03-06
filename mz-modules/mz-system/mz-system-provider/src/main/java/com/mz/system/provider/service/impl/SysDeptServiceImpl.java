package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.utils.MzUtils;
import com.mz.common.utils.TreeUtils;
import com.mz.system.model.entity.SysDeptEntity;
import com.mz.system.model.vo.SysDeptTree;
import com.mz.system.model.vo.req.SysDeptReqVo;
import com.mz.system.provider.dao.SysDeptDao;
import com.mz.system.provider.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    @Override
    public List<SysDeptEntity> queryList(SysDeptReqVo sysDeptReqVo) {
        return  list(Wrappers.<SysDeptEntity>lambdaQuery()
                .eq(MzUtils.notEmpty(sysDeptReqVo.getStatus()), SysDeptEntity::getStatus, sysDeptReqVo.getStatus())
                .like(StringUtils.isNoneBlank(sysDeptReqVo.getDeptName()), SysDeptEntity::getDeptName, sysDeptReqVo.getDeptName())
        );
    }

    @Override
    public List<SysDeptTree> getDeptTree() {
        LambdaQueryWrapper<SysDeptEntity> queryWrapper = Wrappers.<SysDeptEntity>lambdaQuery()
                .select(SysDeptEntity::getDeptId,
                        SysDeptEntity::getDeptName,
                        SysDeptEntity::getParentId,
                        SysDeptEntity::getOrderNum);
        List<SysDeptEntity> entities = baseMapper.selectList(queryWrapper);

        List<SysDeptTree> deptTrees = entities.stream().map(m -> {
            SysDeptTree deptTree = new SysDeptTree();
            deptTree.setDeptId(m.getDeptId());
            deptTree.setDeptName(m.getDeptName());
            deptTree.setParentId(m.getParentId());
            deptTree.setOrderNum(m.getOrderNum());
            return deptTree;
        }).collect(Collectors.toList());
        List<SysDeptTree> trees = TreeUtils.generateTrees(deptTrees, false);
        return CollectionUtils.isEmpty(trees) ? Collections.emptyList() : trees;
    }

    @Override
    public List<SysDeptTree> getDeptListTree(SysDeptReqVo sysDeptReqVo) {
        List<SysDeptTree> allDept = baseMapper.getAllByDeptNameAndStatus(sysDeptReqVo.getDeptName(), sysDeptReqVo.getStatus());
        List<SysDeptTree> trees = TreeUtils.generateTrees(allDept, false);
        return CollectionUtils.isEmpty(trees) ? Collections.emptyList() : trees;
    }

    @Override
    public boolean saveDept(SysDeptReqVo sysDeptReqVo) {
        SysDeptEntity sysDeptEntity = BeanUtil.copyProperties(sysDeptReqVo, SysDeptEntity.class);
        int insert = baseMapper.insert(sysDeptEntity);
        if (insert > 0) {
            String ancestors = getAncestorsByDeptId(sysDeptReqVo.getParentId());
            sysDeptEntity.setAncestors(ancestors + "," + sysDeptEntity.getDeptId());
            super.updateById(sysDeptEntity);
            return true;
        }
        return false;
    }

    @Override
    public String getAncestorsByDeptId(Long deptId) {
        SysDeptEntity deptEntity = lambdaQuery().eq(SysDeptEntity::getDeptId, deptId).select(SysDeptEntity::getAncestors).one();
        return deptEntity.getAncestors();
    }

    @Override
    public boolean updateDeptById(SysDeptReqVo sysDeptReqVo) {
        SysDeptEntity sysDept = getById(sysDeptReqVo.getDeptId());
        SysDeptEntity sysDeptEntity = BeanUtil.copyProperties(sysDeptReqVo, SysDeptEntity.class);
        if (Objects.nonNull(sysDept)) {
            if (!sysDeptReqVo.getParentId().equals(sysDept .getParentId())) {
                String ancestors = getAncestorsByDeptId(sysDeptReqVo.getParentId());
                sysDeptEntity.setAncestors(ancestors + "," + sysDeptReqVo.getDeptId());
            }
        }
        return super.updateById(sysDeptEntity);
    }
}