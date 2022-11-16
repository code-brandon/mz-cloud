package com.mz.system.provider.controller;

import cn.hutool.core.lang.tree.Tree;
import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.dto.SysMenuDto;
import com.mz.system.model.entity.SysMenuEntity;
import com.mz.system.model.vo.res.MenuResVo;
import com.mz.system.provider.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 菜单权限表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "菜单权限表")
@RestController
@RequestMapping("admin/sysmenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 分页查询所有数据
     * @param params  请求集合
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页码",dataTypeClass = String.class, paramType = "query",example="1"),
            @ApiImplicitParam(name="limit",value="每页显示记录数",dataTypeClass = String.class, paramType = "query",example="10")
    })
    @ApiOperation("分页查询所有数据")
    @GetMapping("/list")
    public R<PageUtils<SysMenuEntity>> list(@RequestParam Map<String, Object> params){
        PageUtils<SysMenuEntity> page = sysMenuService.queryPage(params);
        return R.ok(page);
    }

    /**
     * 获取菜单树
     * @return
     */
    @ApiOperation(value = "获取用户菜单树",notes = "根据用户查询用户具有的菜单")
    @GetMapping("/getUserMenuTree")
    public R<List<MenuResVo>> getUserMenuTree(){
        List<SysMenuDto> menuTree = sysMenuService.getUserMenuTree();
        return R.ok(sysMenuService.buildMenus(menuTree));
    }

    @ApiOperation(value = "获取菜单树",notes = "下拉框中选择")
    @GetMapping("/getMenuTree")
    public R<List<Tree<Long>>> getMenuTree(){
        List<Tree<Long>> menuTree = sysMenuService.getMenuTree();
        return R.ok(menuTree);
    }



    @ApiOperation(value = "获取菜单列表树",notes = "菜单列表")
    @GetMapping("/getMenuListTree")
    public R<List<Tree<Long>>> getMenuListTree(){
        List<Tree<Long>> menuTree = sysMenuService.getMenuListTree();
        return R.ok(menuTree);
    }


    /**
     * 通过主键查询单条数据
     * @param menuId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="menuId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{menuId}")
    public R<SysMenuEntity> info(@PathVariable("menuId") Long menuId){
            SysMenuEntity sysMenu = sysMenuService.getById(menuId);

        return R.ok(sysMenu);
    }

    /**
     * 保存数据
     * @param sysMenu 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysMenuEntity sysMenu){
            sysMenuService.save(sysMenu);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     * @param sysMenu 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean>  update(@RequestBody SysMenuEntity sysMenu){
            sysMenuService.updateById(sysMenu);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     * @param menuIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody Long[] menuIds){
            sysMenuService.removeByIds(Arrays.asList(menuIds));

        return R.ok(Boolean.TRUE);
    }

}
