package com.mz.system.provider.controller;

import cn.hutool.core.lang.tree.Tree;
import com.mz.common.core.entity.R;
import com.mz.system.model.entity.SysDeptEntity;
import com.mz.system.model.vo.SysDeptTree;
import com.mz.system.model.vo.req.SysDeptReqVo;
import com.mz.system.provider.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;


/**
 * 部门表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "部门表")
@RestController
@RequestMapping("admin/sysdept")
@RequiredArgsConstructor
public class SysDeptController {
    private final SysDeptService sysDeptService;

    /**
     * 查询所有数据
     * @param sysDeptReqVo  请求集合
     * @return 所有数据
     */
    @ApiOperation("查询所有数据")
    @PreAuthorize("@pms.hasPermission('system:dept:query')")
    @GetMapping("/getList")
    public R<List<SysDeptEntity>> getList(SysDeptReqVo sysDeptReqVo){
        List<SysDeptEntity> list = sysDeptService.queryList(sysDeptReqVo);
        return R.ok(list);
    }

    @ApiOperation("获取部门列表树")
    @PreAuthorize("@pms.hasPermission('system:dept:query')")
    @GetMapping("/getDeptListTree")
    public R<List<SysDeptTree>> getDeptListTree(SysDeptReqVo sysDeptReqVo) {
        List<SysDeptTree> trees = sysDeptService.getDeptListTree(sysDeptReqVo);
        return R.ok(trees);
    }

    /**
     * 获取部门树列表
     * @return
     */
    @ApiOperation(value = "获取部门树",tags = {"下拉列表"})
    @GetMapping("/getDeptTree")
    public R<List<Tree<Long>>> getDeptTree() {
        List<Tree<Long>> trees = sysDeptService.getDeptTree();
        return R.ok(trees);
    }


    /**
     * 通过主键查询单条数据
     * @param deptId 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @PreAuthorize("@pms.hasPermission('system:dept:query')")
    @GetMapping("/info/{deptId:\\d+}")
    public R<SysDeptEntity> info(@PathVariable("deptId") Long deptId){
        SysDeptEntity sysDept = sysDeptService.getById(deptId);
        return R.ok(sysDept);
    }

    /**
     * 保存数据
     * @param sysDeptReqVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PreAuthorize("@pms.hasPermission('system:dept:save')")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysDeptReqVo sysDeptReqVo){
        boolean save = sysDeptService.saveDept(sysDeptReqVo);
        return R.okOrFail(save, "保存");
    }

    /**
     * 修改数据
     * @param sysDeptReqVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PreAuthorize("@pms.hasPermission('system:dept:update')")
    @PutMapping("/update")
    public R<Boolean>  update(@RequestBody SysDeptReqVo sysDeptReqVo){
        boolean update = sysDeptService.updateDeptById(sysDeptReqVo);
        return R.okOrFail(update, "更新");
    }

    /**
     * 删除数据
     * @param deptIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @PreAuthorize("@pms.hasPermission('system:dept:delete')")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody @Validated @Size(min = 1) Long[] deptIds){
        boolean remove = sysDeptService.removeByIds(Arrays.asList(deptIds));
        return R.okOrFail(remove, "删除");
    }

}
