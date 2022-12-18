package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysUserRoleEntity;
import com.mz.system.model.vo.req.SysRoleBindUserReqVo;
import com.mz.system.model.vo.req.SysUserByRoleIdReqVo;
import com.mz.system.model.vo.res.SysUserResVo;
import com.mz.system.provider.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;


/**
 * 用户和角色关联表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "用户和角色关联表")
@RestController
@RequestMapping("admin/sysuserrole")
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 根据角色ID查询用户分页数据
     *
     * @param params            分页数据
     * @param userByRoleIdResVo 分页条件
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataTypeClass = String.class, paramType = "query", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", dataTypeClass = String.class, paramType = "query", example = "10")
    })
    @ApiOperation(value = "根据角色ID查询用户分页数据", tags = "角色查询分配用户")
    @PostMapping("/getUserPage")
    public R<PageUtils<SysUserResVo>> getUserPage(@ApiIgnore @RequestParam Map<String, Object> params, @Valid @RequestBody SysUserByRoleIdReqVo userByRoleIdResVo) {
        PageUtils<SysUserResVo> userResVoPage = sysUserRoleService.getUserPageByRoleId(params, userByRoleIdResVo);
        return R.ok(userResVoPage);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataTypeClass = String.class, paramType = "query", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", dataTypeClass = String.class, paramType = "query", example = "10")
    })
    @ApiOperation(value = "根据角色ID查询不是此角色用户分页数据", tags = "角色选择用户")
    @PostMapping("/getNotThisRoleUserPage")
    public R<PageUtils<SysUserResVo>> getNotThisRoleUserPage(@ApiIgnore @RequestParam Map<String, Object> params, @Valid @RequestBody SysUserByRoleIdReqVo userByRoleIdResVo) {
        PageUtils<SysUserResVo> userResVoPage = sysUserRoleService.getNotThisRoleUserPage(params, userByRoleIdResVo);
        return R.ok(userResVoPage);
    }

    /**
     * 保存角色绑定用户的关系
     *
     * @param roleBindUserReqVo 角色绑定用户请求数据
     * @return
     */
    @ApiOperation(value = "保存角色绑定用户的关系", tags = "角色绑定用户")
    @PostMapping("/save/roleBindUser")
    public R<Boolean> saveRoleBindUser(@Valid @RequestBody SysRoleBindUserReqVo roleBindUserReqVo) {
        sysUserRoleService.saveRoleBindUser(roleBindUserReqVo);

        return R.ok(Boolean.TRUE);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "主键", dataTypeClass = Long.class, paramType = "path", example = "1")
    })
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/info/{userId}")
    public R<SysUserRoleEntity> info(@PathVariable("userId") Long userId) {
        SysUserRoleEntity sysUserRole = sysUserRoleService.getById(userId);

        return R.ok(sysUserRole);
    }

    /**
     * 保存数据
     *
     * @param sysUserRole 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@Valid @RequestBody SysUserRoleEntity sysUserRole) {
        sysUserRoleService.save(sysUserRole);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改数据
     *
     * @param sysUserRole 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean> update(@Valid @RequestBody SysUserRoleEntity sysUserRole) {
        sysUserRoleService.updateById(sysUserRole);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除数据
     *
     * @param roleBindUserReqVo 角色绑定用户请求数据
     * @return 删除结果
     */
    @ApiOperation("解除角色关联的用户")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@Valid @RequestBody SysRoleBindUserReqVo roleBindUserReqVo) {
        sysUserRoleService.deleteByRoleIdAndUserIds(roleBindUserReqVo);

        return R.ok(Boolean.TRUE);
    }

}
