package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.validated.groups.InsertField;
import com.mz.common.validated.groups.UpdateField;
import com.mz.system.model.entity.SysPostEntity;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.req.SysPostReqVo;
import com.mz.system.provider.service.SysPostService;
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
import java.util.List;
import java.util.Map;


/**
 * 岗位信息表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "岗位信息表")
@RestController
@RequestMapping("admin/syspost")
@RequiredArgsConstructor
public class SysPostController {
    private final SysPostService sysPostService;

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
    @PreAuthorize("@pms.hasPermission('system:post:query')")
    @PostMapping("/page")
    public R<PageUtils<SysPostEntity>> page(@ApiIgnore @RequestParam Map<String, Object> params, @RequestBody SysPostReqVo sysPostReqVo) {
        PageUtils<SysPostEntity> page = sysPostService.queryPage(params, sysPostReqVo);
        return R.ok(page);
    }

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @ApiOperation("所有数据")
    @PostMapping("/list")
    @PreAuthorize("@pms.hasPermission('system:post:query')")
    public R<List<SysPostEntity>> list() {
        List<SysPostEntity> posts = sysPostService.list();
        return R.ok(posts);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param postId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "主键", dataTypeClass = Long.class, paramType = "path", example = "1")
    })
    @ApiOperation("通过主键查询单条数据")
    @PreAuthorize("@pms.hasPermission('system:post:query')")
    @GetMapping("/info/{postId:\\d+}")
    public R<SysPostEntity> info(@PathVariable("postId") Long postId) {
        SysPostEntity sysPost = sysPostService.getById(postId);

        return R.ok(sysPost);
    }

    /**
     * 保存数据
     *
     * @param sysPostReqVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PreAuthorize("@pms.hasPermission('system:post:save')")
    @PostMapping("/save")
    public R<Boolean> save(@Validated(InsertField.class) @RequestBody SysPostReqVo sysPostReqVo) {
        boolean save = sysPostService.savePost(sysPostReqVo);

        return R.okOrFail(save, "保存");
    }

    /**
     * 修改数据
     *
     * @param sysPostReqVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PreAuthorize("@pms.hasPermission('system:post:update')")
    @PutMapping("/update")
    public R<Boolean> update(@Validated(UpdateField.class) @RequestBody SysPostReqVo sysPostReqVo) {
        boolean update = sysPostService.updatePostById(sysPostReqVo);

        return R.okOrFail(update, "更新");
    }

    /**
     * 修改状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改状态")
    @PreAuthorize("@pms.hasPermission('system:post:update')")
    @PutMapping("/update/status")
    public R<Boolean> updateStatus(@Validated @RequestBody SysIdAndStatusReqVo idAndStatusReqVo) {
        boolean updateStatus = sysPostService.updateStatus(idAndStatusReqVo);

        return R.okOrFail(updateStatus, "修改");
    }

    /**
     * 删除数据
     *
     * @param postIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @PreAuthorize("@pms.hasPermission('system:post:delete')")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody @Validated @Size(min = 1) Long[] postIds) {
        boolean remove = sysPostService.removeByIds(Arrays.asList(postIds));

        return R.okOrFail(remove, "删除");
    }

}
