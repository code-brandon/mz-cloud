package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.security.annotation.Ignore;
import com.mz.system.model.dto.SysLogininforDto;
import com.mz.system.model.entity.SysLogininforEntity;
import com.mz.system.provider.service.SysLogininforService;
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
 * 系统访问记录
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "系统访问记录")
@RestController
@RequestMapping("admin/syslogininfor")
@RequiredArgsConstructor
public class SysLogininforController {
    private final SysLogininforService sysLogininforService;

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
    public R<PageUtils<SysLogininforEntity>> list(@ApiIgnore @RequestParam Map<String, Object> params){
        PageUtils<SysLogininforEntity>  page = sysLogininforService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 通过主键查询单条数据
     * @param infoId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="infoId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{infoId}")
    public R<SysLogininforEntity> info(@PathVariable("infoId") Long infoId){
        SysLogininforEntity sysLogininfor = sysLogininforService.getById(infoId);
        return R.ok(sysLogininfor);
    }

    /**
     * 保存数据
     * @param sysLogininfor 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping("/save")
    @Ignore
    public R<Boolean> save(@RequestBody SysLogininforDto sysLogininfor){
        boolean save = sysLogininforService.saveLogininfor(sysLogininfor);
        return R.okOrFail(save, "保存");
    }

    /**
     * 删除数据
     * @param infoIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody @Validated @Size(min = 1) Long[] infoIds){
        boolean remove = sysLogininforService.removeByIds(Arrays.asList(infoIds));
        return R.okOrFail(remove, "删除");
    }

}
