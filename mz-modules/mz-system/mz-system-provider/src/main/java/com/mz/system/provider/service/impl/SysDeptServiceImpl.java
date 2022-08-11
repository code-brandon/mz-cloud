package com.mz.system.provider.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.core.constants.Constant;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysDeptEntity;
import com.mz.system.provider.dao.SysDeptDao;
import com.mz.system.provider.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDeptEntity> page = this.page(
                new Query<SysDeptEntity>().getPage(params),
                new QueryWrapper<SysDeptEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<Tree<Long>> getDeptTree() {
        List<SysDeptEntity> entities = baseMapper.selectList(null);

        List<TreeNode<Long>> nodeList = entities.stream().map(m -> {
            TreeNode<Long> objectTreeNode = new TreeNode<>();
            objectTreeNode.setId(m.getDeptId());
            objectTreeNode.setName(m.getDeptName());
            objectTreeNode.setParentId(m.getParentId());
            objectTreeNode.setWeight(m.getOrderNum());
            return objectTreeNode;
        }).collect(Collectors.toList());
        List<Tree<Long>> treeList = TreeUtil.build(nodeList, Constant.ROOT_NODE);
        log.info("{}", JSONUtil.toJsonStr(treeList));

        return treeList;
    }

}