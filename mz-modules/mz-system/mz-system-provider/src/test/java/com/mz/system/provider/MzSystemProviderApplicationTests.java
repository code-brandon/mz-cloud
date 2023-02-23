package com.mz.system.provider;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mz.common.constant.MzConstant;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.utils.TreeUtils;
import com.mz.system.model.dto.SysMenuDto;
import com.mz.system.model.entity.SysUserEntity;
import com.mz.system.model.vo.SysDeptTree;
import com.mz.system.model.vo.SysUserVo;
import com.mz.system.model.vo.search.SysUserSearchVo;
import com.mz.system.provider.dao.SysDeptDao;
import com.mz.system.provider.dao.SysMenuDao;
import com.mz.system.provider.dao.SysUserRoleDao;
import com.mz.system.provider.service.SysUserService;
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
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {
        Object appName = ((StandardEnvironment) environment).getSystemProperties().get("@appId");
        log.info("应用名称 = {}", appName);
    }


    @Test
    void getMenuTree() {
        List<SysMenuDto> menuByUserId = sysMenuDao.getMenuByUserId(1L, true);
        log.info("{}", menuByUserId);

        List<TreeNode<Long>> nodeList = menuByUserId.stream().map(m -> {
            TreeNode<Long> objectTreeNode = new TreeNode<>();
            objectTreeNode.setId(m.getMenuId());
            objectTreeNode.setName(m.getMenuName());
            objectTreeNode.setParentId(m.getParentId());
            objectTreeNode.setWeight(m.getOrderNum());
            return objectTreeNode;
        }).collect(Collectors.toList());
        List<Tree<Long>> treeList = TreeUtil.build(nodeList, MzConstant.ROOT_NODE);
        log.info("{}", JSONUtil.toJsonStr(treeList));
    }

    @Test
    void getDeptTreeCondition() {
        List<SysDeptTree> entities = sysDeptDao.getAllByDeptNameAndStatus("", "1");
        List<SysDeptTree> deptTrees = TreeUtils.generateTrees(entities, false);
        System.out.println(JSONUtil.toJsonStr(deptTrees));
    }

    @Test
    void getUserByName() {
        SysUserEntity sysUser = sysUserService.getUserByName("test");
        log.info("{}", JSONUtil.toJsonStr(sysUser));
    }

    @Test
    void getUserPageByRoleId() {
        SysUserSearchVo sysUserByRoleIdReqVo = new SysUserSearchVo();
        sysUserByRoleIdReqVo.setRoleId(1L);
        sysUserByRoleIdReqVo.setStatus("0");
        IPage<SysUserVo> userPage = sysUserRoleDao.selectUserPageByRoleId(new Page<>(), sysUserByRoleIdReqVo);
        log.info("{}", JSONUtil.toJsonStr(new PageUtils<>(userPage)));
    }

    @Test
    void getNotThisRoleUserPage() {
        SysUserSearchVo sysUserByRoleIdReqVo = new SysUserSearchVo();
        sysUserByRoleIdReqVo.setRoleId(1L);
        sysUserByRoleIdReqVo.setStatus("0");
        IPage<SysUserVo> userPage = sysUserRoleDao.selectNotThisRoleUserPage(new Page<>(), sysUserByRoleIdReqVo);
        log.info("{}", JSONUtil.toJsonStr(new PageUtils<>(userPage)));
    }

}
