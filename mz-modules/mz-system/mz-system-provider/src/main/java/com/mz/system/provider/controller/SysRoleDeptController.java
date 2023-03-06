package com.mz.system.provider.controller;

import com.mz.common.constant.MzConstant;
import com.mz.common.core.entity.R;
import com.mz.common.log.annotation.MzLog;
import com.mz.common.log.enums.BusinessType;
import com.mz.system.model.entity.SysRoleDeptEntity;
import com.mz.system.model.vo.req.SysRoleDeptReqVo;
import com.mz.system.provider.service.SysRoleDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * 角色和部门关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "角色和部门关联表")
@RestController
@RequestMapping("admin/sysroledept")
@RequiredArgsConstructor
public class SysRoleDeptController {
    private final SysRoleDeptService sysRoleDeptService;

    /**
     * 分页根据角色ID查询部门
     *
     * @param roleId 请求集合
     * @return 分页数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", dataTypeClass = Long.class, paramType = "path", example = "1")
    })
    @ApiOperation(value = "根据角色ID查询部门", tags = {"回显角色绑定的部门"})
    @GetMapping("/list/{roleId}")
    public R<List<SysRoleDeptEntity>> list(@PathVariable("roleId") Long roleId) {

        List<SysRoleDeptEntity> roleDepts = sysRoleDeptService.listByIds(Collections.singletonList(roleId));
        return R.ok(roleDepts);
    }

    /**
     * 保存角色所在部门
     *
     * @param sysRoleDeptReqVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存角色部门")
    @MzLog(title = "角色绑定部门", businessType = BusinessType.SAVE)
    @RolesAllowed({MzConstant.ADMIN})
    @PostMapping("/save")
    public R<Boolean> save(@Valid @RequestBody SysRoleDeptReqVo sysRoleDeptReqVo) {
        boolean save = sysRoleDeptService.saveRoleDept(sysRoleDeptReqVo);
        return R.okOrFail(save, "保存");
    }

    /**
     * 删除角色绑定的部门
     *
     * @param roleIds 角色ID集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除角色绑定的部门")
    @MzLog(title = "角色绑定部门", businessType = BusinessType.REMOVE)
    @RolesAllowed({MzConstant.ADMIN})
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody @Validated @Size(min = 1) Long[] roleIds) {
        boolean remove = sysRoleDeptService.removeByIds(Arrays.asList(roleIds));
        return R.okOrFail(remove, "删除");
    }

}
