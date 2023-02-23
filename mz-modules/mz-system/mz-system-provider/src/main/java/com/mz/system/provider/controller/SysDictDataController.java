package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.utils.MzUtils;
import com.mz.common.validated.groups.IdField;
import com.mz.system.model.entity.SysDictDataEntity;
import com.mz.system.model.vo.req.SysDictDataReqVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.search.SysDictDataSearchVo;
import com.mz.system.provider.service.SysDictDataService;
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
import java.util.Collections;
import java.util.List;
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
@RequiredArgsConstructor
public class SysDictDataController {
    private final SysDictDataService sysDictDataService;

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
    @PreAuthorize("@pms.hasPermission('system:dict:query')")
    @PostMapping("/page")
    public R<PageUtils<SysDictDataEntity>> page(@RequestBody @Validated SysDictDataSearchVo dataSearchVo, @ApiIgnore @RequestParam Map<String, Object> params) {
        PageUtils<SysDictDataEntity> page = sysDictDataService.queryPage(params, dataSearchVo);
        return R.ok(page);
    }


    /**
     * 通过字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return
     */
    @ApiOperation("通过字典类型查询字典数据")
    @GetMapping("/type/{dictType}")
    public R<List<SysDictDataEntity>> info(@PathVariable("dictType") String dictType) {
        List<SysDictDataEntity> dictDatas = sysDictDataService.listByDictType(dictType);
        return R.ok(MzUtils.notEmpty(dictDatas) ? dictDatas : Collections.emptyList());
    }


    /**
     * 通过主键查询单条数据
     *
     * @param dictCode 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictCode", value = "主键", dataTypeClass = Long.class, paramType = "path", example = "1")
    })
    @ApiOperation("通过主键查询单条数据")
    @PreAuthorize("@pms.hasPermission('system:dict:query')")
    @GetMapping("/info/{dictCode}")
    public R<SysDictDataEntity> info(@PathVariable("dictCode") Long dictCode) {
        SysDictDataEntity sysDictData = sysDictDataService.getById(dictCode);
        return R.ok(sysDictData);
    }

    /**
     * 保存数据
     *
     * @param sysDictDataVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PreAuthorize("@pms.hasPermission('system:dict:save')")
    @PostMapping("/save")
    public R<Boolean> save(@Validated @RequestBody SysDictDataReqVo sysDictDataVo) {
        boolean save = sysDictDataService.saveDictData(sysDictDataVo);
        return R.okOrFail(save, "保存");
    }

    /**
     * 修改数据
     *
     * @param sysDictDataVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PreAuthorize("@pms.hasPermission('system:dict:update')")
    @PutMapping("/update")
    public R<Boolean> update(@Validated(IdField.class) @RequestBody SysDictDataReqVo sysDictDataVo) {
        boolean update = sysDictDataService.updateDictDataById(sysDictDataVo);
        return R.okOrFail(update, "更新");
    }

    /**
     * 修改状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改状态")
    @PreAuthorize("@pms.hasPermission('system:dict:update')")
    @PutMapping("/update/status")
    public R<Boolean> updateStatus(@Validated @RequestBody SysIdAndStatusReqVo idAndStatusReqVo) {
        boolean updateStatus = sysDictDataService.updateStatus(idAndStatusReqVo);
        return R.okOrFail(updateStatus, "修改");
    }

    /**
     * 删除数据
     *
     * @param dictCodes 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @PreAuthorize("@pms.hasPermission('system:dict:delete')")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody @Validated @Size(min = 1) Long[] dictCodes) {
        boolean remove = sysDictDataService.removeByIds(Arrays.asList(dictCodes));
        return R.okOrFail(remove, "删除");
    }

}
