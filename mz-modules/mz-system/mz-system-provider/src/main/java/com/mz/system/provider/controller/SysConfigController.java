package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.validated.groups.UpdateField;
import com.mz.system.model.entity.SysConfigEntity;
import com.mz.system.model.vo.req.SysConfigReqVo;
import com.mz.system.provider.service.SysConfigService;
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
import java.util.Map;


/**
 * 参数配置表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "参数配置表")
@RestController
@RequestMapping("admin/sysconfig")
@RequiredArgsConstructor
public class SysConfigController {
    private final SysConfigService sysConfigService;

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
    @PreAuthorize("@pms.hasPermission('system:config:query')")
    @PostMapping("/page")
    public R<PageUtils<SysConfigEntity>> page(@RequestBody SysConfigReqVo sysConfigReqVo, @ApiIgnore @RequestParam Map<String, Object> params){
        PageUtils<SysConfigEntity> page = sysConfigService.queryPage(params,sysConfigReqVo);
        return R.ok(page);
    }


    /**
     * 通过主键查询单条数据
     * @param configId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="configId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @PreAuthorize("@pms.hasPermission('system:config:query')")
    @GetMapping("/info/{configId}")
    public R<SysConfigEntity> info(@PathVariable("configId") Long configId){
        SysConfigEntity sysConfig = sysConfigService.getById(configId);
        return R.ok(sysConfig);
    }

    /**
     * 保存数据
     * @param sysConfigReqVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PreAuthorize("@pms.hasPermission('system:config:save')")
    @PostMapping("/save")
    public R<Boolean> save(@Validated @RequestBody SysConfigReqVo sysConfigReqVo){
        boolean save = sysConfigService.saveConfig(sysConfigReqVo);
        return R.okOrFail(save, "保存");
    }

    /**
     * 修改数据
     * @param sysConfigReqVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PreAuthorize("@pms.hasPermission('system:config:update')")
    @PutMapping("/update")
    public R<Boolean> update(@Validated(UpdateField.class) @RequestBody SysConfigReqVo sysConfigReqVo){
        boolean update = sysConfigService.updateConfigById(sysConfigReqVo);
        return R.okOrFail(update, "更新");
    }

    /**
     * 删除数据
     * @param configIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @PreAuthorize("@pms.hasPermission('system:config:delete')")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody @Validated @Size(min = 1) Long[] configIds){
        boolean remove = sysConfigService.removeByIds(Arrays.asList(configIds));
        return R.okOrFail(remove, "删除");
    }

}
