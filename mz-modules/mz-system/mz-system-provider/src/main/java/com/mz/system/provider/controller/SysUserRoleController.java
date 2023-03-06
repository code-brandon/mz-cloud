package com.mz.system.provider.controller;

import com.mz.common.constant.MzConstant;
import com.mz.common.core.entity.R;
import com.mz.common.log.annotation.MzLog;
import com.mz.common.log.enums.BusinessType;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.vo.SysUserVo;
import com.mz.system.model.vo.req.SysRoleBindUserReqVo;
import com.mz.system.model.vo.search.SysUserSearchVo;
import com.mz.system.provider.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
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
@RequiredArgsConstructor
public class SysUserRoleController {
    private final SysUserRoleService sysUserRoleService;

    /**
     * 根据角色ID查询用户分页数据
     *
     * @param params       分页数据
     * @param userSearchVo 分页条件
     * @return 分页信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataTypeClass = String.class, paramType = "query", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", dataTypeClass = String.class, paramType = "query", example = "10")
    })
    @ApiOperation(value = "根据角色ID查询用户分页数据", tags = "角色查询分配用户")
    @RolesAllowed({MzConstant.ADMIN})
    @PostMapping("/getUserPage")
    public R<PageUtils<SysUserVo>> getUserPage(@ApiIgnore @RequestParam Map<String, Object> params, @Valid @RequestBody SysUserSearchVo userSearchVo) {
        PageUtils<SysUserVo> userResVoPage = sysUserRoleService.getUserPageByRoleId(params, userSearchVo);
        return R.ok(userResVoPage);
    }

    /**
     * 根据角色ID查询不是此角色用户分页数据
     *
     * @param params       分页数据
     * @param userSearchVo 分页条件
     * @return 分页信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataTypeClass = String.class, paramType = "query", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", dataTypeClass = String.class, paramType = "query", example = "10")
    })
    @ApiOperation(value = "根据角色ID查询不是此角色用户分页数据", tags = "角色选择用户")
    @RolesAllowed({MzConstant.ADMIN})
    @PostMapping("/getNotThisRoleUserPage")
    public R<PageUtils<SysUserVo>> getNotThisRoleUserPage(@ApiIgnore @RequestParam Map<String, Object> params, @Valid @RequestBody SysUserSearchVo userSearchVo) {
        PageUtils<SysUserVo> userResVoPage = sysUserRoleService.getNotThisRoleUserPage(params, userSearchVo);
        return R.ok(userResVoPage);
    }

    /**
     * 保存角色绑定用户的关系
     *
     * @param roleBindUserReqVo 角色绑定用户请求数据
     * @return 保存结果
     */
    @ApiOperation(value = "保存角色绑定用户的关系", tags = "角色绑定用户")
    @MzLog(title = "用户关联角色", businessType = BusinessType.SAVE)
    @RolesAllowed({MzConstant.ADMIN})
    @PostMapping("/save/roleBindUser")
    public R<Boolean> saveRoleBindUser(@Valid @RequestBody SysRoleBindUserReqVo roleBindUserReqVo) {
        boolean save = sysUserRoleService.saveRoleBindUser(roleBindUserReqVo);
        return R.okOrFail(save, "保存");
    }


    /**
     * 解除角色关联的用户
     *
     * @param roleBindUserReqVo 角色绑定用户请求数据
     * @return 删除结果
     */
    @ApiOperation("解除角色关联的用户")
    @MzLog(title = "用户关联角色", businessType = BusinessType.REMOVE)
    @RolesAllowed({MzConstant.ADMIN})
    @DeleteMapping("/delete")
    public R<Boolean> delete(@Valid @RequestBody SysRoleBindUserReqVo roleBindUserReqVo) {
        boolean delete = sysUserRoleService.deleteByRoleIdAndUserIds(roleBindUserReqVo);
        return R.okOrFail(delete, "删除");
    }

}
