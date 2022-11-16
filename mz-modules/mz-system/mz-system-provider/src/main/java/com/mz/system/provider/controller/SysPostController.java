package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysPostEntity;
import com.mz.system.provider.service.SysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
public class SysPostController {
    @Autowired
    private SysPostService sysPostService;

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
    @PostMapping("/page")
    public R<PageUtils<SysPostEntity>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageUtils<SysPostEntity> page = sysPostService.queryPage(params);
        return R.ok(page);
    }

    @ApiOperation("所有数据")
    @PostMapping("/list")
    public R<List<SysPostEntity>> list(){
        List<SysPostEntity> posts = sysPostService.list();
        return R.ok(posts);
    }


    /**
     * 通过主键查询单条数据
     * @param postId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="postId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{postId}")
    public R<SysPostEntity> info(@PathVariable("postId") Long postId){
            SysPostEntity sysPost = sysPostService.getById(postId);

        return R.ok(sysPost);
    }

    /**
     * 保存数据
     * @param sysPost 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysPostEntity sysPost){
            sysPostService.save(sysPost);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     * @param sysPost 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean>  update(@RequestBody SysPostEntity sysPost){
            sysPostService.updateById(sysPost);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     * @param postIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody Long[] postIds){
            sysPostService.removeByIds(Arrays.asList(postIds));

        return R.ok(Boolean.TRUE);
    }

}
