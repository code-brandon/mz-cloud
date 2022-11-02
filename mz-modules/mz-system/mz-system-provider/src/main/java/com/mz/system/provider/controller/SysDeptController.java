package com.mz.system.provider.controller;

import cn.hutool.core.lang.tree.Tree;
import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysDeptEntity;
import com.mz.system.provider.service.SysDeptService;
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
 * 部门表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "部门表")
@RestController
@RequestMapping("admin/sysdept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

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
    @GetMapping("/page")
    public R<PageUtils<SysDeptEntity>> page(@RequestParam Map<String, Object> params){
        PageUtils<SysDeptEntity> page = sysDeptService.queryPage(params);
        return R.ok(page);
    }

    @ApiOperation("获取部门列表树")
    @GetMapping("/getDeptListTree")
    public R<List<Tree<Long>>> getDeptListTree() {
        List<Tree<Long>> trees = sysDeptService.getDeptListTree();
        return R.ok(trees);
    }

    /**
     * 获取部门树列表
     * @return
     */
    @ApiOperation("获取部门树")
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
    @ApiImplicitParams({
            @ApiImplicitParam(name="deptId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{deptId}")
    public R<SysDeptEntity> info(@PathVariable("deptId") Long deptId){
            SysDeptEntity sysDept = sysDeptService.getById(deptId);

        return R.ok(sysDept);
    }

    /**
     * 保存数据
     * @param sysDept 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysDept",value="sysDept 实体对象",dataTypeClass = SysDeptEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysDeptEntity sysDept){
            sysDeptService.save(sysDept);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     * @param sysDept 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysDept",value="sysDept 实体对象",dataTypeClass = SysDeptEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean>  update(@RequestBody SysDeptEntity sysDept){
            sysDeptService.updateById(sysDept);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     * @param deptIds 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysDept",value="sysDept 实体对象",dataTypeClass = SysDeptEntity.class, paramType = "body",example="{'deptIds':[zahngsan,lisi]}")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody Long[] deptIds){
            sysDeptService.removeByIds(Arrays.asList(deptIds));

        return R.ok(Boolean.TRUE);
    }

}
