package com.mz.system.provider.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.dto.SysMenuDto;
import com.mz.system.model.entity.SysMenuEntity;
import com.mz.system.model.vo.res.MenuResVo;

import java.util.List;
import java.util.Map;

/**
 * 菜单权限表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    PageUtils<SysMenuEntity> queryPage(Map<String, Object> params);

    /**
     * 菜单树
     * @return
     */
    List<SysMenuDto> getUserMenuTree();

    List<Tree<Long>> getMenuTree();

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    List<SysMenuDto> getChildPerms(List<SysMenuDto> list, Long parentId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<MenuResVo> buildMenus(List<SysMenuDto> menus);

    List<Tree<Long>> getMenuListTree();
}

