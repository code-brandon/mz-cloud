package com.mz.system.provider.controller;

import com.mz.common.constant.MzConstant;
import com.mz.common.core.entity.R;
import com.mz.common.log.annotation.MzLog;
import com.mz.common.log.enums.BusinessType;
import com.mz.common.validated.groups.UpdateField;
import com.mz.system.model.vo.req.SysRoleMenuReqVo;
import com.mz.system.provider.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Arrays;


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
@RequiredArgsConstructor
public class SysRoleMenuController {
    private final SysRoleMenuService sysRoleMenuService;


    /**
     * 保存角色菜单
     *
     * @param sysRoleMenuReqVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存角色菜单")
    @MzLog(title = "角色绑定部门", businessType = BusinessType.SAVE)
    @RolesAllowed({MzConstant.ADMIN})
    @PostMapping("/saveRoleMenu")
    public R<Boolean> save(@Valid @RequestBody SysRoleMenuReqVo sysRoleMenuReqVo) {
        boolean save = sysRoleMenuService.saveRoleMenu(sysRoleMenuReqVo);
        return R.okOrFail(save, "保存");
    }

    /**
     * 修改角色菜单
     *
     * @param sysRoleMenuReqVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改角色菜单")
    @MzLog(title = "角色绑定部门", businessType = BusinessType.UPDATE)
    @RolesAllowed({MzConstant.ADMIN})
    @PutMapping("/updateRoleMenu")
    public R<Boolean> update(@Validated(UpdateField.class) @RequestBody SysRoleMenuReqVo sysRoleMenuReqVo) {
        boolean update = sysRoleMenuService.updateRoleMenuById(sysRoleMenuReqVo);
        return R.okOrFail(update, "更新");
    }

    /**
     * 删除角色绑定的菜单
     *
     * @param roleIds 角色集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除角色绑定的菜单")
    @MzLog(title = "角色绑定菜单", businessType = BusinessType.REMOVE)
    @RolesAllowed({MzConstant.ADMIN})
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody @Validated @Size(min = 1) Long[] roleIds) {
        boolean remove = sysRoleMenuService.removeByIds(Arrays.asList(roleIds));
        return R.okOrFail(remove, "删除");
    }

}
