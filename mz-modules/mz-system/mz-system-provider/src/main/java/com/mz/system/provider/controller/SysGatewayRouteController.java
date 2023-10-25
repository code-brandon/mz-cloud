package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.gateway.entity.MzGatewayRoute;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysGatewayRouteEntity;
import com.mz.system.provider.service.SysGatewayRouteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 网关路由表
 *
 * @author 小政同学 it_xiaozheng@163.com
 * @email 1911298402@qq.com
 * @date 2023-09-25 19:31:07
 */
@Api(tags = "网关路由表")
@RestController
@RequestMapping("admin/sysgatewayroute")
public class SysGatewayRouteController {
    @Autowired
    private SysGatewayRouteService sysGatewayRouteService;

    /**
     * 分页条件查询所有数据
     *
     * @param params 请求集合
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataTypeClass = String.class, paramType = "query", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", dataTypeClass = String.class, paramType = "query", example = "10")
    })
    @ApiOperation("分页条件查询所有数据")
    @PostMapping("/queryPage")
    public R<PageUtils<SysGatewayRouteEntity>> queryPage(@RequestBody @ApiParam(name = "网关路由表", value = "网关路由表 实体对象", required = true) SysGatewayRouteEntity sysGatewayRoute, @RequestParam @ApiIgnore() Map<String, Object> params) {
        PageUtils<SysGatewayRouteEntity> page = sysGatewayRouteService.queryPage(sysGatewayRoute, params);
        return R.ok(page);
    }

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @ApiOperation("查询所有数据")
    @GetMapping("/list")
    public R<List<MzGatewayRoute>> queryList() {
        List<MzGatewayRoute> mzGatewayRoutes = sysGatewayRouteService.listGatewayRoute();
        return R.ok(mzGatewayRoutes);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param routeId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", value = "主键", dataTypeClass = String.class, paramType = "path", example = "1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{routeId}")
    public R<MzGatewayRoute> info(@PathVariable("routeId") String routeId) {
        MzGatewayRoute mzGatewayRoute = sysGatewayRouteService.getGatewayRouteById(routeId);

        return R.ok(mzGatewayRoute);
    }

    /**
     * 保存数据
     *
     * @param gatewayRouteReqVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody @ApiParam(name = "网关路由表", value = "网关路由表 实体对象", required = true) MzGatewayRoute gatewayRouteReqVo) {
        boolean save = sysGatewayRouteService.saveGatewayRoute(gatewayRouteReqVo);
        return R.okOrFail(save, "保存");
    }

    /**
     * 保存数据
     *
     * @param gatewayRoutes 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存或更新数据")
    @PostMapping("/saveOrUpdate")
    public R<Boolean> saveOrUpdate(@RequestBody @ApiParam(name = "网关路由表", value = "网关路由表 实体对象", required = true) List<MzGatewayRoute> gatewayRoutes) {
        boolean save = sysGatewayRouteService.saveOrUpdateGatewayRoute(gatewayRoutes);
        return R.okOrFail(save, "保存");
    }


    /**
     * 修改数据
     *
     * @param gatewayRoute 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean> update(@RequestBody @ApiParam(name = "网关路由表", value = "网关路由表 实体对象", required = true) MzGatewayRoute gatewayRoute) {
        boolean update = sysGatewayRouteService.updateGatewayRouteById(gatewayRoute);
        return R.okOrFail(update, "删除");
    }

    /**
     * 删除数据
     *
     * @param routeIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody @Validated @Size(min = 1) Long[] routeIds) {

        boolean remove = sysGatewayRouteService.removeByIds(Arrays.asList(routeIds));
        return R.okOrFail(remove, "删除");
    }


    /**
     * 重置网关路由
     *
     * @return 重置网关路由
     */
    @ApiOperation("重置网关路由")
    @PutMapping("/resetRoute")
    public R<Boolean> resetRoute() {
        boolean reset = sysGatewayRouteService.resetRoute();
        return R.okOrFail(reset, "");
    }

}
