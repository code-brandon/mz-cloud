package com.mz.system.provider.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.system.model.dto.SysMenuDto;
import com.mz.system.model.entity.SysMenuEntity;
import com.mz.system.model.vo.MenuMenuVo;
import com.mz.system.model.vo.SysMenuTree;
import com.mz.system.model.vo.req.SysMenuReqVo;
import com.mz.system.model.vo.res.MenuResVo;

import java.util.List;

/**
 * 菜单权限表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 获取用户菜单树
     * @return 用户所具有的菜单树
     */
    List<SysMenuDto> getUserMenuTree();


    /**
     * 获取菜单树
     * @return
     */
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

    /**
     * 获取菜单列表树
     * @param sysMenuVo 实体对象
     * @return 菜单树列表
     */
    List<SysMenuTree> getMenuListTree(SysMenuReqVo sysMenuVo);

    /**
     * 保存菜单信息
     * @param menuMenuVo 实体对象
     * @return true：成功，false：失败
     */
    boolean saveMenu(MenuMenuVo menuMenuVo);

    /**
     * 修改菜单信息
     * @param menuMenuVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateMenuById(MenuMenuVo menuMenuVo);
}

