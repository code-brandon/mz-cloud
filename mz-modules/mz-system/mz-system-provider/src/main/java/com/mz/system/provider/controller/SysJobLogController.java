package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysJobLogEntity;
import com.mz.system.provider.service.SysJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class SysJobLogController {
    @Autowired
    private SysJobLogService sysJobLogService;

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
    public R<SysJobLogEntity> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysJobLogService.queryPage(params);
        return R.ok().data(page);
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
    @GetMapping("/info/{jobLogId}")
    public R<SysJobLogEntity> info(@PathVariable("jobLogId") Long jobLogId){
            SysJobLogEntity sysJobLog = sysJobLogService.getById(jobLogId);

        return R.ok().data(sysJobLog);
    }

    /**
     * 保存数据
     * @param sysJobLog 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysJobLog",value="sysJobLog 实体对象",dataTypeClass = SysJobLogEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R save(@RequestBody SysJobLogEntity sysJobLog){
            sysJobLogService.save(sysJobLog);

        return R.ok();
    }

    /**
     * 修改数据
     * @param sysJobLog 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysJobLog",value="sysJobLog 实体对象",dataTypeClass = SysJobLogEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R update(@RequestBody SysJobLogEntity sysJobLog){
            sysJobLogService.updateById(sysJobLog);

        return R.ok();
    }

    /**
     * 删除数据
     * @param jobLogIds 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysJobLog",value="sysJobLog 实体对象",dataTypeClass = SysJobLogEntity.class, paramType = "body",example="{'jobLogIds':[zahngsan,lisi]}")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] jobLogIds){
            sysJobLogService.removeByIds(Arrays.asList(jobLogIds));

        return R.ok();
    }

}