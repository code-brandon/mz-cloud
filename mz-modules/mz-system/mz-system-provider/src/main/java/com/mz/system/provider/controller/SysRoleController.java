package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.vo.req.SysRoleReqVo;
import com.mz.system.model.vo.res.SysRoleResVo;
import com.mz.system.provider.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 角色信息表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "角色信息表")
@RestController
@RequestMapping("admin/sysrole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

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
    @PostMapping("/page")
    public R<PageUtils<SysRoleEntity>> page(@ApiIgnore @RequestParam Map<String, Object> params, @RequestBody SysRoleReqVo sysRoleReqVo) {
        PageUtils<SysRoleEntity> page = sysRoleService.queryPage(params, sysRoleReqVo);
        return R.ok(page);
    }

    @ApiOperation("所有数据")
    @PostMapping("/list")
    public R<List<SysRoleEntity>> list() {
        List<SysRoleEntity> posts = sysRoleService.list();
        return R.ok(posts);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param roleId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "主键", dataTypeClass = Long.class, paramType = "path", example = "1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{roleId}")
    public R<SysRoleResVo> info(@PathVariable("roleId") Long roleId) {
        SysRoleResVo sysRole = sysRoleService.getRoleById(roleId);
        return R.ok(sysRole);
    }

    /**
     * 保存数据
     *
     * @param sysRoleResVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping("/saveRole")
    public R<Boolean> save(@RequestBody SysRoleResVo sysRoleResVo) {
        sysRoleService.saveRole(sysRoleResVo);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     *
     * @param sysRoleResVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean> update(@RequestBody SysRoleResVo sysRoleResVo) {
        sysRoleService.updateRoleById(sysRoleResVo);

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
        sysRoleService.removeRoleByIds(Arrays.asList(roleIds));

        return R.ok(Boolean.TRUE);
    }

}
