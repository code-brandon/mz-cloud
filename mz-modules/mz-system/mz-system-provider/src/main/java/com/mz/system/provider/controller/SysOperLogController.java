package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.security.annotation.Ignore;
import com.mz.system.model.dto.SysOperLogDto;
import com.mz.system.model.entity.SysOperLogEntity;
import com.mz.system.provider.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Map;


/**
 * 操作日志记录
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "操作日志记录")
@RestController
@RequestMapping("admin/sysoperlog")
@RequiredArgsConstructor
public class SysOperLogController {
    private final SysOperLogService sysOperLogService;

    /**
     * 分页查询操作日志
     *
     * @param params 分页参数
     * @return 分页数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataTypeClass = String.class, paramType = "query", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", dataTypeClass = String.class, paramType = "query", example = "10")
    })
    @ApiOperation("分页查询操作日志")
    @GetMapping("/page")
    public R<PageUtils<SysOperLogEntity>> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageUtils<SysOperLogEntity> page = sysOperLogService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 通过操作ID查询单条数据
     *
     * @param operId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "operId", value = "主键", dataTypeClass = Long.class, paramType = "path", example = "1")
    })
    @ApiOperation("通过操作ID查询单条数据")
    @GetMapping("/info/{operId}")
    public R<SysOperLogEntity> info(@PathVariable("operId") Long operId) {
        SysOperLogEntity sysOperLog = sysOperLogService.getById(operId);

        return R.ok(sysOperLog);
    }

    /**
     * 保存操作日志
     *
     * @param sysOperLog 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存操作日志")
    @PostMapping("/save")
    @Ignore
    public R<Boolean> save(@RequestBody SysOperLogDto sysOperLog) {
        boolean save = sysOperLogService.saveOperLog(sysOperLog);
        return R.okOrFail(save, "保存");
    }

    /**
     * 删除日志记录
     *
     * @param operIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除日志记录")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody @Validated @Size(min = 1) Long[] operIds) {
        boolean remove = sysOperLogService.removeByIds(Arrays.asList(operIds));
        return R.okOrFail(remove, "删除");
    }

}
