package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysJobLogEntity;
import com.mz.system.provider.service.SysJobLogService;
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
 * 定时任务调度日志表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "定时任务调度日志表")
@RestController
@RequestMapping("admin/sysjoblog")
@RequiredArgsConstructor
public class SysJobLogController {
    private final SysJobLogService sysJobLogService;

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
    public R<PageUtils<SysJobLogEntity> > list(@ApiIgnore @RequestParam Map<String, Object> params){
        PageUtils<SysJobLogEntity> page = sysJobLogService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 通过主键查询单条数据
     * @param jobLogId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="jobLogId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{jobLogId:\\d+}")
    public R<SysJobLogEntity> info(@PathVariable("jobLogId") Long jobLogId){
            SysJobLogEntity sysJobLog = sysJobLogService.getById(jobLogId);

        return R.ok(sysJobLog);
    }

    /**
     * 保存数据
     * @param sysJobLog 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysJobLogEntity sysJobLog){
            sysJobLogService.save(sysJobLog);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     * @param sysJobLog 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean>  update(@RequestBody SysJobLogEntity sysJobLog){
            sysJobLogService.updateById(sysJobLog);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     * @param jobLogIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody @Validated @Size(min = 1) Long[] jobLogIds){
            sysJobLogService.removeByIds(Arrays.asList(jobLogIds));

        return R.ok(Boolean.TRUE);
    }

}
