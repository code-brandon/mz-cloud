package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysDictTypeEntity;
import com.mz.system.provider.service.SysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 字典类型表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "字典类型表")
@RestController
@RequestMapping("admin/sysdicttype")
public class SysDictTypeController {
    @Autowired
    private SysDictTypeService sysDictTypeService;

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
    @PostMapping("/page")
    public R<PageUtils<SysDictTypeEntity>> page(@RequestParam Map<String, Object> params){
        PageUtils<SysDictTypeEntity> page = sysDictTypeService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 通过主键查询单条数据
     * @param dictId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="dictId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{dictId}")
    public R<SysDictTypeEntity> info(@PathVariable("dictId") Long dictId){
            SysDictTypeEntity sysDictType = sysDictTypeService.getById(dictId);

        return R.ok(sysDictType);
    }

    /**
     * 保存数据
     * @param sysDictType 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysDictType",value="sysDictType 实体对象",dataTypeClass = SysDictTypeEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysDictTypeEntity sysDictType){
            sysDictTypeService.save(sysDictType);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     * @param sysDictType 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysDictType",value="sysDictType 实体对象",dataTypeClass = SysDictTypeEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean>  update(@RequestBody SysDictTypeEntity sysDictType){
            sysDictTypeService.updateById(sysDictType);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     * @param dictIds 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysDictType",value="sysDictType 实体对象",dataTypeClass = SysDictTypeEntity.class, paramType = "body",example="{'dictIds':[zahngsan,lisi]}")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody Long[] dictIds){
            sysDictTypeService.removeByIds(Arrays.asList(dictIds));

        return R.ok(Boolean.TRUE);
    }

}
