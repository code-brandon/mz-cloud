package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysOperLogEntity;
import com.mz.system.provider.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class SysOperLogController {
    @Autowired
    private SysOperLogService sysOperLogService;

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
    public R<PageUtils<SysOperLogEntity>> list(@RequestParam Map<String, Object> params){
        PageUtils<SysOperLogEntity> page = sysOperLogService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 通过主键查询单条数据
     * @param operId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="operId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{operId}")
    public R<SysOperLogEntity> info(@PathVariable("operId") Long operId){
            SysOperLogEntity sysOperLog = sysOperLogService.getById(operId);

        return R.ok(sysOperLog);
    }

    /**
     * 保存数据
     * @param sysOperLog 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysOperLog",value="sysOperLog 实体对象",dataTypeClass = SysOperLogEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysOperLogEntity sysOperLog){
            sysOperLogService.save(sysOperLog);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     * @param sysOperLog 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysOperLog",value="sysOperLog 实体对象",dataTypeClass = SysOperLogEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean>  update(@RequestBody SysOperLogEntity sysOperLog){
            sysOperLogService.updateById(sysOperLog);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     * @param operIds 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysOperLog",value="sysOperLog 实体对象",dataTypeClass = SysOperLogEntity.class, paramType = "body",example="{'operIds':[zahngsan,lisi]}")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody Long[] operIds){
            sysOperLogService.removeByIds(Arrays.asList(operIds));

        return R.ok(Boolean.TRUE);
    }

}
