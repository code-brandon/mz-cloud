package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.log.annotation.MzLog;
import com.mz.common.log.enums.BusinessType;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysRoleEntity;
import com.mz.system.model.vo.SysRoleVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.search.SysRoleSearchVo;
import com.mz.system.provider.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Size;
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
@RequiredArgsConstructor
public class SysRoleController {
    private final SysRoleService sysRoleService;

    /**
     * 分页查询角色信息
     *
     * @param params 分页参数
     * @return 分页数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataTypeClass = String.class, paramType = "query", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", dataTypeClass = String.class, paramType = "query", example = "10")
    })
    @ApiOperation("分页查询角色信息")
    @PreAuthorize("@pms.hasPermission('system:role:query')")
    @PostMapping("/page")
    public R<PageUtils<SysRoleEntity>> page(@ApiIgnore @RequestParam Map<String, Object> params, @RequestBody SysRoleSearchVo roleSearchVo) {
        PageUtils<SysRoleEntity> page = sysRoleService.queryPage(params, roleSearchVo);
        return R.ok(page);
    }

    /**
     * 查询所有角色信息
     *
     * @return 所有角色信息
     */
    @ApiOperation("查询所有角色信息")
    @PostMapping("/list")
    @PreAuthorize("@pms.hasPermission('system:role:query')")
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
    @PreAuthorize("@pms.hasPermission('system:role:query')")
    @GetMapping("/info/{roleId}")
    public R<SysRoleVo> info(@PathVariable("roleId") Long roleId) {
        SysRoleVo sysRole = sysRoleService.getRoleById(roleId);
        return R.ok(sysRole);
    }

    /**
     * 保存角色
     *
     * @param sysRoleVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @MzLog(title = "角色管理", businessType = BusinessType.SAVE)
    @PreAuthorize("@pms.hasPermission('system:role:save')")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysRoleVo sysRoleVo) {
        boolean save = sysRoleService.saveRole(sysRoleVo);
        return R.okOrFail(save, "保存");
    }

    /**
     * 修改角色
     *
     * @param sysRoleVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改角色")
    @MzLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@pms.hasPermission('system:role:update')")
    @PutMapping("/update")
    public R<Boolean> update(@RequestBody SysRoleVo sysRoleVo) {
        boolean update = sysRoleService.updateRoleById(sysRoleVo);
        return R.okOrFail(update, "更新");
    }

    /**
     * 修改角色状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改角色状态")
    @MzLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@pms.hasPermission('system:role:update')")
    @PutMapping("/update/status")
    public R<Boolean> updateStatus(@Validated @RequestBody SysIdAndStatusReqVo idAndStatusReqVo) {
        boolean updateStatus = sysRoleService.updateStatus(idAndStatusReqVo);
        return R.okOrFail(updateStatus, "修改");
    }

    /**
     * 删除角色数据
     *
     * @param roleIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除角色数据")
    @MzLog(title = "角色管理", businessType = BusinessType.REMOVE)
    @PreAuthorize("@pms.hasPermission('system:role:delete')")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody @Validated @Size(min = 1) Long[] roleIds) {
        boolean remove = sysRoleService.removeRoleByIds(Arrays.asList(roleIds));

        return R.okOrFail(remove, "删除");
    }

}
