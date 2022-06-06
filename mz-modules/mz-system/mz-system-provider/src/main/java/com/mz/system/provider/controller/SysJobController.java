package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysJobEntity;
import com.mz.system.provider.service.SysJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 定时任务调度表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "定时任务调度表")
@RestController
@RequestMapping("admin/sysjob")
public class SysJobController {
    @Autowired
    private SysJobService sysJobService;

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
    public R<SysJobEntity> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysJobService.queryPage(params);
        return R.ok().data(page);
    }


    /**
     * 通过主键查询单条数据
     * @param jobId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="jobId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{jobId}")
    public R<SysJobEntity> info(@PathVariable("jobId") Long jobId){
            SysJobEntity sysJob = sysJobService.getById(jobId);

        return R.ok().data(sysJob);
    }

    /**
     * 保存数据
     * @param sysJob 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysJob",value="sysJob 实体对象",dataTypeClass = SysJobEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R save(@RequestBody SysJobEntity sysJob){
            sysJobService.save(sysJob);

        return R.ok();
    }

    /**
     * 修改数据
     * @param sysJob 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysJob",value="sysJob 实体对象",dataTypeClass = SysJobEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R update(@RequestBody SysJobEntity sysJob){
            sysJobService.updateById(sysJob);

        return R.ok();
    }

    /**
     * 删除数据
     * @param jobIds 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysJob",value="sysJob 实体对象",dataTypeClass = SysJobEntity.class, paramType = "body",example="{'jobIds':[zahngsan,lisi]}")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] jobIds){
            sysJobService.removeByIds(Arrays.asList(jobIds));

        return R.ok();
    }

}
