package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysUserPostEntity;
import com.mz.system.provider.service.SysUserPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 用户与岗位关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "用户与岗位关联表")
@RestController
@RequestMapping("admin/sysuserpost")
public class SysUserPostController {
    @Autowired
    private SysUserPostService sysUserPostService;

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
    public R<SysUserPostEntity> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserPostService.queryPage(params);
        return R.ok().data(page);
    }


    /**
     * 通过主键查询单条数据
     * @param userId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="主键",dataTypeClass = Long.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{userId}")
    public R<SysUserPostEntity> info(@PathVariable("userId") Long userId){
            SysUserPostEntity sysUserPost = sysUserPostService.getById(userId);

        return R.ok().data(sysUserPost);
    }

    /**
     * 保存数据
     * @param sysUserPost 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysUserPost",value="sysUserPost 实体对象",dataTypeClass = SysUserPostEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R save(@RequestBody SysUserPostEntity sysUserPost){
            sysUserPostService.save(sysUserPost);

        return R.ok();
    }

    /**
     * 修改数据
     * @param sysUserPost 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysUserPost",value="sysUserPost 实体对象",dataTypeClass = SysUserPostEntity.class, paramType = "body",example="{'name':'zahngsan'}")
    })
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R update(@RequestBody SysUserPostEntity sysUserPost){
            sysUserPostService.updateById(sysUserPost);

        return R.ok();
    }

    /**
     * 删除数据
     * @param userIds 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="sysUserPost",value="sysUserPost 实体对象",dataTypeClass = SysUserPostEntity.class, paramType = "body",example="{'userIds':[zahngsan,lisi]}")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] userIds){
            sysUserPostService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
