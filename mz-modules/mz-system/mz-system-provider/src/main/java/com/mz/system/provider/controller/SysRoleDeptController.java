package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysRoleDeptEntity;
import com.mz.system.provider.service.SysRoleDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


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
     * @param params  请求集合
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页码",dataTypeClass = String.class, paramType = "query",example="1"),
            @ApiImplicitParam(name="limit",value="每页显示记录数",dataTypeClass = String.class, paramType = "query",example="10")
    })
    @ApiOperation("分页查询所有数据")
    @GetMapping("/list")
    public R<SysRoleDeptEntity> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleDeptService.queryPage(params);
        return R.ok().data(page);
    }


    /**
     * 通过主键查询单条数据
     * @param roleId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{roleId}")
    public R<SysRoleDeptEntity> info(@PathVariable("roleId") Long roleId){
            SysRoleDeptEntity sysRoleDept = sysRoleDeptService.getById(roleId);

        return R.ok().data(sysRoleDept);
    }

    /**
     * 保存数据
     * @param sysRoleDept 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysRoleDept",value="sysRoleDept 实体对象",dataTypeClass = SysRoleDeptEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R save(@RequestBody SysRoleDeptEntity sysRoleDept){
            sysRoleDeptService.save(sysRoleDept);

        return R.ok();
    }

    /**
     * 修改数据
     * @param sysRoleDept 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysRoleDept",value="sysRoleDept 实体对象",dataTypeClass = SysRoleDeptEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R update(@RequestBody SysRoleDeptEntity sysRoleDept){
            sysRoleDeptService.updateById(sysRoleDept);

        return R.ok();
    }

    /**
     * 删除数据
     * @param roleIds 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysRoleDept",value="sysRoleDept 实体对象",dataTypeClass = SysRoleDeptEntity.class, paramType = "body",example="{'roleIds':[zahngsan,lisi]}")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] roleIds){
            sysRoleDeptService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}
