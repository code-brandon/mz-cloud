package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.constant.MzConstant;
import com.mz.common.core.exception.MzException;
import com.mz.common.security.entity.MzUserDetailsSecurity;
import com.mz.common.security.utils.MzSecurityUtils;
import com.mz.common.utils.MzUtils;
import com.mz.common.utils.TreeUtils;
import com.mz.common.validated.groups.UpdateField;
import com.mz.system.model.dto.SysMenuDto;
import com.mz.system.model.entity.SysMenuEntity;
import com.mz.system.model.vo.MenuButtonVo;
import com.mz.system.model.vo.MenuDirectoryVo;
import com.mz.system.model.vo.MenuMenuVo;
import com.mz.system.model.vo.SysMenuTree;
import com.mz.system.model.vo.req.SysMenuReqVo;
import com.mz.system.model.vo.res.MenuResVo;
import com.mz.system.model.vo.res.MetaResVo;
import com.mz.system.provider.dao.SysMenuDao;
import com.mz.system.provider.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    /**
     * 当前登录用户的菜单列表
     * @return
     */
    @Override
    public List<SysMenuDto> getUserMenuTree() {
        MzUserDetailsSecurity sysUserSecurity = MzSecurityUtils.getMzSysUserSecurity();
        List<SysMenuDto> menus = baseMapper.getMenuByUserId(sysUserSecurity .getUserId(), sysUserSecurity .isIfAdmin());
        return getChildPerms(menus, MzConstant.ROOT_NODE);
    }

    @Override
    public List<Tree<Long>> getMenuTree() {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = Wrappers.<SysMenuEntity>lambdaQuery()
                .select(SysMenuEntity::getMenuId,
                        SysMenuEntity::getMenuName,
                        SysMenuEntity::getParentId,
                        SysMenuEntity::getOrderNum);
        List<SysMenuEntity> entities = baseMapper.selectList(queryWrapper);

        List<TreeNode<Long>> nodeList = entities.stream().map(m -> {
            TreeNode<Long> objectTreeNode = new TreeNode<>();
            objectTreeNode.setId(m.getMenuId());
            objectTreeNode.setName(m.getMenuName());
            objectTreeNode.setParentId(m.getParentId());
            objectTreeNode.setWeight(m.getOrderNum());
            return objectTreeNode;
        }).collect(Collectors.toList());
        List<Tree<Long>> build = TreeUtil.build(nodeList, MzConstant.ROOT_NODE);
        return CollectionUtils.isEmpty(build) ? Collections.emptyList() : build;
    }

    @Override
    public List<SysMenuTree> getMenuListTree(SysMenuReqVo sysMenuVo) {


        List<SysMenuTree> allMenu = baseMapper.getAllByMenuNameAndStatus(sysMenuVo.getMenuName(), sysMenuVo.getStatus());
        /*List<SysMenuEntity> entities = list(Wrappers.<SysMenuEntity> lambdaQuery()
                .eq(StringUtils.isNotEmpty(sysMenuVo.getStatus()),SysMenuEntity::getStatus, sysMenuVo.getStatus())
                .like(StringUtils.isNotEmpty(sysMenuVo.getMenuName()),SysMenuEntity::getMenuName, sysMenuVo.getMenuName()));

        List<TreeNode<Long>> nodeList = entities.stream().map(m -> {
            TreeNode<Long> objectTreeNode = new TreeNode<>();
            objectTreeNode.setId(m.getMenuId());
            objectTreeNode.setWeight(m.getOrderNum());
            objectTreeNode.setExtra(BeanMapUtils.beanToMap(m));
            return objectTreeNode;
        }).collect(Collectors.toList());
        List<Tree<Long>> build = TreeUtil.build(nodeList, MzConstant.ROOT_NODE);*/

        List<SysMenuTree> trees = TreeUtils.generateTrees(allMenu, false);
        return CollectionUtils.isEmpty(trees) ? Collections.emptyList() : trees;
    }

    /**
     * 保存菜单信息
     * @param menuMenuVo
     * @return
     */
    @Override
    public boolean saveMenu(MenuMenuVo menuMenuVo) {
        Assert.notNull(menuMenuVo);
        Assert.notBlank(menuMenuVo.getMenuType());
        SysMenuEntity sysMenuEntity = getSysMenuEntityByType(menuMenuVo);
        return super.save(sysMenuEntity);
    }

    @Override
    public boolean updateMenuById(MenuMenuVo menuMenuVo) {
        Assert.notNull(menuMenuVo);
        Assert.notBlank(menuMenuVo.getMenuType());
        SysMenuEntity sysMenuEntity = getSysMenuEntityByType(menuMenuVo, UpdateField.class);
        return super.updateById(sysMenuEntity);
    }


    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<MenuResVo> buildMenus(List<SysMenuDto> menus) {
        List<MenuResVo> routers = new LinkedList<>();
        for (SysMenuDto menu : menus) {
            MenuResVo router = new MenuResVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setIsFrame(menu.getIsFrame());
            router.setMeta(new MetaResVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("0", menu.getIsCache().toString()), menu.getPath()));
            List<SysMenuDto> cMenus = menu.getChildren();
            if (!cMenus.isEmpty()  && MzConstant.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<MenuResVo> childrenList = new ArrayList<>();
                MenuResVo children = new MenuResVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaResVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache().toString()), menu.getPath()));
                children.setQuery(menu.getQuery());
                children.setIsFrame(menu.getIsFrame());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaResVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<MenuResVo> childrenList = new ArrayList<>();
                MenuResVo children = new MenuResVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(MzConstant.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaResVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                children.setIsFrame(menu.getIsFrame());
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    @Override
    public List<SysMenuDto> getChildPerms(List<SysMenuDto> list, Long parentId) {
        List<SysMenuDto> returnList = new ArrayList<>();
        for (Iterator<SysMenuDto> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenuDto t = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId().equals(parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenuDto> list, SysMenuDto t) {
        // 得到子节点列表
        List<SysMenuDto> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenuDto tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenuDto> getChildList(List<SysMenuDto> list, SysMenuDto t) {
        List<SysMenuDto> tlist = new ArrayList<>();
        Iterator<SysMenuDto> it = list.iterator();
        while (it.hasNext()) {
            SysMenuDto n = it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuDto> list, SysMenuDto t) {
        return !getChildList(list, t).isEmpty();
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String  getRouteName(SysMenuDto menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenuDto menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && MzConstant.TYPE_DIR.equals(menu.getMenuType()) && MzConstant.NO_FRAME == menu.getIsFrame()) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenuDto menu) {
        String component = MzConstant.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = MzConstant.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = MzConstant.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenuDto menu) {
        return menu.getParentId().intValue() == 0 && MzConstant.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(MzConstant.NO_FRAME);
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenuDto menu) {
        return menu.getIsFrame().equals(MzConstant.NO_FRAME) && MzUtils.ishttp(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenuDto menu) {
        return menu.getParentId().intValue() != 0 && MzConstant.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{MzConstant.HTTP, MzConstant.HTTPS},
                new String[]{"", ""});
    }

    /**
     * 根据菜单类型进行类型转换并进行字段校验
     * @param menuMenuVo
     * @return
     */
    private SysMenuEntity getSysMenuEntityByType(MenuMenuVo menuMenuVo, Class<?>... groups) {
        SysMenuEntity sysMenuEntity = new SysMenuEntity();
        // 菜单类型（M目录 C菜单 F按钮）
        switch (menuMenuVo.getMenuType().toUpperCase()) {
            case "M":
                MenuDirectoryVo menuDirectoryVo = new MenuDirectoryVo();
                BeanUtil.copyProperties(menuMenuVo, menuDirectoryVo);
                MzUtils.validate(menuDirectoryVo,groups);
                BeanUtil.copyProperties(menuDirectoryVo, sysMenuEntity);
                break;
            case "C":
                MzUtils.validate(menuMenuVo,groups);
                BeanUtil.copyProperties(menuMenuVo, sysMenuEntity);
                break;
            case "F":
                MenuButtonVo menuButtonVo = new MenuButtonVo();
                BeanUtil.copyProperties(menuMenuVo, menuButtonVo);
                MzUtils.validate(menuButtonVo,groups);
                BeanUtil.copyProperties(menuButtonVo, sysMenuEntity);
                break;
            default:
                throw new MzException("没有对应的MenuType类型");
        }
        return sysMenuEntity;
    }
}