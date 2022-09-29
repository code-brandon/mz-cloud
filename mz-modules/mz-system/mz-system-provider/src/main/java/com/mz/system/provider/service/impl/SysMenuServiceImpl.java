package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.core.constants.Constant;
import com.mz.common.core.constants.UserConstants;
import com.mz.common.core.utils.MzWebUtils;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.common.security.entity.MzUserDetailsSecurity;
import com.mz.common.security.utils.MzSecurityUtils;
import com.mz.system.model.dto.SysMenuDto;
import com.mz.system.model.entity.SysMenuEntity;
import com.mz.system.model.vo.res.MenuResVo;
import com.mz.system.model.vo.res.MetaResVo;
import com.mz.system.provider.dao.SysMenuDao;
import com.mz.system.provider.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMenuEntity> page = this.page(
                new Query<SysMenuEntity>().getPage(params),
                new QueryWrapper<SysMenuEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<SysMenuDto> getMenuTree() {
        MzUserDetailsSecurity sysUserSecurity = MzSecurityUtils.getMzSysUserSecurity();
        List<SysMenuDto> menus = baseMapper.getMenuByUserId(sysUserSecurity .getUserId(), sysUserSecurity .isIfAdmin());
        return getChildPerms(menus, Constant.ROOT_NODE);
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<MenuResVo> buildMenus(List<SysMenuDto> menus) {
        List<MenuResVo> routers = new LinkedList<MenuResVo>();
        for (SysMenuDto menu : menus) {
            MenuResVo router = new MenuResVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setIsFrame(menu.getIsFrame());
            router.setMeta(new MetaResVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache().toString()), menu.getPath()));
            List<SysMenuDto> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<MenuResVo> childrenList = new ArrayList<MenuResVo>();
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
                List<MenuResVo> childrenList = new ArrayList<MenuResVo>();
                MenuResVo children = new MenuResVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
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
        List<SysMenuDto> returnList = new ArrayList<SysMenuDto>();
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
        List<SysMenuDto> tlist = new ArrayList<SysMenuDto>();
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
        return getChildList(list, t).size() > 0;
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
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType()) && UserConstants.NO_FRAME == menu.getIsFrame()) {
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
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
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
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenuDto menu) {
        return menu.getIsFrame().equals(UserConstants.NO_FRAME) && MzWebUtils.ishttp(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenuDto menu) {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constant.HTTP, Constant.HTTPS},
                new String[]{"", ""});
    }
}