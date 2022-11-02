package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysDictDataEntity;
import com.mz.system.provider.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 字典数据表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "字典数据表")
@RestController
@RequestMapping("admin/sysdictdata")
public class SysDictDataController {
    @Autowired
    private SysDictDataService sysDictDataService;

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
    public R<PageUtils<SysDictDataEntity>> page(@RequestParam Map<String, Object> params){
        PageUtils<SysDictDataEntity> page = sysDictDataService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 通过主键查询单条数据
     * @param dictCode 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="dictCode",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{dictCode}")
    public R<SysDictDataEntity> info(@PathVariable("dictCode") Long dictCode){
            SysDictDataEntity sysDictData = sysDictDataService.getById(dictCode);

        return R.ok(sysDictData);
    }

    /**
     * 保存数据
     * @param sysDictData 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysDictData",value="sysDictData 实体对象",dataTypeClass = SysDictDataEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysDictDataEntity sysDictData){
            sysDictDataService.save(sysDictData);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     * @param sysDictData 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysDictData",value="sysDictData 实体对象",dataTypeClass = SysDictDataEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean>  update(@RequestBody SysDictDataEntity sysDictData){
            sysDictDataService.updateById(sysDictData);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     * @param dictCodes 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysDictData",value="sysDictData 实体对象",dataTypeClass = SysDictDataEntity.class, paramType = "body",example="{'dictCodes':[zahngsan,lisi]}")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody Long[] dictCodes){
            sysDictDataService.removeByIds(Arrays.asList(dictCodes));

        return R.ok(Boolean.TRUE);
    }

}
