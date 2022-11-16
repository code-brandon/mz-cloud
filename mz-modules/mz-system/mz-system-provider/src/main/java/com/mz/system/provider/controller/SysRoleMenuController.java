package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysRoleMenuEntity;
import com.mz.system.model.vo.req.SysRoleMenuReqVo;
import com.mz.system.provider.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;


/**
 * 角色和菜单关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "角色和菜单关联表")
@RestController
@RequestMapping("admin/sysrolemenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询所有数据
     *
     * @param params 请求集合
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataTypeClass = String.class, paramType = "query", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", dataTypeClass = String.class, paramType = "query", example = "10")
    })
    @ApiOperation("分页查询所有数据")
    @GetMapping("/list")
    public R<PageUtils<SysRoleMenuEntity>> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageUtils<SysRoleMenuEntity> page = sysRoleMenuService.queryPage(params);
        return R.ok(page);
    }

    /**
     * 保存数据
     *
     * @param sysRoleMenuReqVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping("/saveRoleMenu")
    public R<Boolean> save(@Valid @RequestBody SysRoleMenuReqVo sysRoleMenuReqVo) {
        sysRoleMenuService.saveRoleMenu(sysRoleMenuReqVo);
        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     *
     * @param sysRoleMenuReqVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PutMapping("/updateRoleMenu")
    public R<Boolean> update(@Valid @RequestBody SysRoleMenuReqVo sysRoleMenuReqVo) {
        Asserts.notNull(sysRoleMenuReqVo.getRoleId(),"roleId不能为空");
        sysRoleMenuService.updateRoleMenuById(sysRoleMenuReqVo);
        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     *
     * @param roleIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody Long[] roleIds) {
        boolean remove = sysRoleMenuService.removeByIds(Arrays.asList(roleIds));
        return remove ? R.ok(Boolean.TRUE) : R.fail(Boolean.FALSE);
    }

}
