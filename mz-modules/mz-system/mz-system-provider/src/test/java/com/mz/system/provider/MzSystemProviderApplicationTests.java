package com.mz.system.provider;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.mz.common.core.constants.Constant;
import com.mz.system.model.dto.SysMenuDto;
import com.mz.system.model.entity.SysDeptEntity;
import com.mz.system.provider.dao.SysDeptDao;
import com.mz.system.provider.dao.SysMenuDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class MzSystemProviderApplicationTests {

    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {
        Object appName = ((StandardEnvironment) environment).getSystemProperties().get("@appId");
        System.out.println("应用名称= " + appName);
    }

    @Test
    void getDeptTree() {
        List<SysDeptEntity> entities = sysDeptDao.selectList(null);
        log.info("{}",entities);

        List<TreeNode<Long>> nodeList = entities.stream().map(m -> {
            TreeNode<Long> objectTreeNode = new TreeNode<>();
            objectTreeNode.setId(m.getDeptId());
            objectTreeNode.setName(m.getDeptName());
            objectTreeNode.setParentId(m.getParentId());
            objectTreeNode.setWeight(m.getOrderNum().intValue());
            return objectTreeNode;
        }).collect(Collectors.toList());
        List<Tree<Long>> treeList = TreeUtil.build(nodeList, Constant.ROOT_NODE);
        log.info("{}", JSONUtil.toJsonStr(treeList));
    }

    @Test
    void getMenuTree() {
        List<SysMenuDto> menuByUserId = sysMenuDao.getMenuByUserId(1L,true);
        log.info("{}",menuByUserId);

        List<TreeNode<Long>> nodeList = menuByUserId.stream().map(m -> {
            TreeNode<Long> objectTreeNode = new TreeNode<>();
            objectTreeNode.setId(m.getMenuId());
            objectTreeNode.setName(m.getMenuName());
            objectTreeNode.setParentId(m.getParentId());
            objectTreeNode.setWeight(m.getOrderNum().intValue());
            return objectTreeNode;
        }).collect(Collectors.toList());
        List<Tree<Long>> treeList = TreeUtil.build(nodeList,Constant.ROOT_NODE);
        log.info("{}", JSONUtil.toJsonStr(treeList));
    }

}
