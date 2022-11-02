package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.system.model.entity.SysRoleDeptEntity;
import com.mz.system.model.vo.req.SysRoleDeptReqVo;
import com.mz.system.provider.service.SysRoleDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class SysRoleDeptController {
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    /**
     * 分页查询所有数据
     *
     * @param roleId 请求集合
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "当前页码", dataTypeClass = Long.class, paramType = "path", example = "1")
    })
    @ApiOperation("分页查询所有数据")
    @GetMapping("/list/{roleId}")
    public R<List<SysRoleDeptEntity>> list(@PathVariable("roleId") Long roleId) {

        List<SysRoleDeptEntity> roleDepts = sysRoleDeptService.listByIds(Collections.singletonList(roleId));
        return R.ok(roleDepts);
    }

    /**
     * 保存数据
     * @param sysRoleDeptReqVo 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysRoleDept",value="sysRoleDept 实体对象",dataTypeClass = SysRoleDeptReqVo.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/saveRoleDept")
    public R<Boolean> save(@Valid @RequestBody SysRoleDeptReqVo sysRoleDeptReqVo){
        sysRoleDeptService.saveRoleDept(sysRoleDeptReqVo);
        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     * @param roleIds 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleIds",value="roleIds 数组对象",dataTypeClass = Long[].class, paramType = "body",example="['1','2']")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody Long[] roleIds){
        boolean remove = sysRoleDeptService.removeByIds(Arrays.asList(roleIds));
        return remove ? R.ok(Boolean.TRUE) : R.fail(Boolean.FALSE);
    }

}
