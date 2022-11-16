package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysNoticeEntity;
import com.mz.system.provider.service.SysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Map;


/**
 * 通知公告表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "通知公告表")
@RestController
@RequestMapping("admin/sysnotice")
public class SysNoticeController {
    @Autowired
    private SysNoticeService sysNoticeService;

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
    public R<PageUtils<SysNoticeEntity>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageUtils<SysNoticeEntity> page = sysNoticeService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 通过主键查询单条数据
     * @param noticeId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="noticeId",value="主键",dataTypeClass = Integer.class, paramType = "path",example="1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{noticeId}")
    public R<SysNoticeEntity> info(@PathVariable("noticeId") Integer noticeId){
            SysNoticeEntity sysNotice = sysNoticeService.getById(noticeId);

        return R.ok(sysNotice);
    }

    /**
     * 保存数据
     * @param sysNotice 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysNoticeEntity sysNotice){
            sysNoticeService.save(sysNotice);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     * @param sysNotice 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean>  update(@RequestBody SysNoticeEntity sysNotice){
            sysNoticeService.updateById(sysNotice);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     * @param noticeIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean>  delete(@RequestBody Integer[] noticeIds){
            sysNoticeService.removeByIds(Arrays.asList(noticeIds));

        return R.ok(Boolean.TRUE);
    }

}
