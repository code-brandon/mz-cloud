package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.log.annotation.MzLog;
import com.mz.common.log.enums.BusinessType;
import com.mz.common.mybatis.annotation.MzIgnoreDataAuth;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.security.annotation.Ignore;
import com.mz.common.security.entity.MzUserDetailsSecurity;
import com.mz.common.security.utils.MzSecurityUtils;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.dto.SysUserLoginLogDto;
import com.mz.system.model.vo.SysUserVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.req.SysUserIdAndPasswdReqVo;
import com.mz.system.provider.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Map;


/**
 * 用户信息表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Api(tags = "用户信息表")
@RestController
@RequestMapping("admin/sysuser")
@RequiredArgsConstructor
public class SysUserController {
    private final SysUserService sysUserService;

    /**
     * 分页查询用户信息
     *
     * @param params 请求集合
     * @return 所有数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataTypeClass = String.class, paramType = "query", example = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示记录数", dataTypeClass = String.class, paramType = "query", example = "10")
    })
    @ApiOperation("分页查询用户数据")
    @PreAuthorize("@pms.hasPermission('system:user:query')")
    @PostMapping("/page")
    public R<PageUtils<SysUserVo>> page(@ApiIgnore @RequestParam Map<String, Object> params, @RequestBody SysUserVo userReqVo) {
        PageUtils<SysUserVo> page = sysUserService.queryPage(params, userReqVo);
        return R.ok(page);
    }

    /**
     * 获取用户名
     *
     * @return 安全用户信息
     */
    @ApiOperation("获取用户名")
    @GetMapping("/getUser")
    public R<MzUserDetailsSecurity> getUser() {
        return R.ok(MzSecurityUtils.getMzSysUserSecurity());
    }

    /**
     * 重置用户密码
     *
     * @param userVo
     * @return 成功失败信息
     */
    @ApiOperation("重置用户密码")
    @MzLog(title = "重置密码", businessType = BusinessType.UPDATE)
    @PreAuthorize("@pms.hasPermission('system:user:resetpwd')")
    @PutMapping("/resetPasswd")
    public R<Boolean> resetPasswd(@Valid @RequestBody SysUserIdAndPasswdReqVo userVo) {
        boolean resetPasswd = sysUserService.resetPasswd(userVo);
        return R.okOrFail(resetPasswd, "重置");
    }


    /**
     * 通过用户ID查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "主键", dataTypeClass = Long.class, paramType = "path", example = "1")
    })
    @ApiOperation("通过用户ID查询单条数据")
    @PreAuthorize("@pms.hasPermission('system:user:query')")
    @GetMapping("/info/{userId}")
    public R<SysUserVo> info(@PathVariable("userId") Long userId) {
        SysUserVo sysUser = sysUserService.getUserById(userId);

        return R.ok(sysUser);
    }

    /**
     * 按用户名获取用户信息 (登录暴漏接口)
     *
     * @param userName 用户名
     * @return 单条数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataTypeClass = String.class, paramType = "path", example = "张三")
    })
    @ApiOperation(value = "按用户名获取用户信息", tags = "登录暴漏接口")
    @PostMapping("/getUserInfoByUserName")
    @Ignore
    @MzIgnoreDataAuth
    public R<SysUserDto> loadUserByUserName(@Valid @RequestParam(value = "userName") @NotBlank String userName) {
        SysUserDto sysUserDto = sysUserService.loadUserByUserName(userName);
        return R.ok(sysUserDto);
    }

    /**
     * 保存用户
     *
     * @param sysUserVo 实体对象
     * @return 新增结果
     */
    @ApiOperation("保存用户")
    @MzLog(title = "用户管理", businessType = BusinessType.SAVE)
    @PreAuthorize("@pms.hasPermission('system:user:save')")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysUserVo sysUserVo) {
        boolean save = sysUserService.saveUser(sysUserVo);
        return R.okOrFail(save, "保存");
    }

    /**
     * 修改用户
     *
     * @param sysUserVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改用户")
    @MzLog(title = "用户管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@pms.hasPermission('system:user:update')")
    @PutMapping("/update")
    public R<Boolean> update(@Valid @RequestBody SysUserVo sysUserVo) {
        boolean update = sysUserService.updateUserById(sysUserVo);
        return R.okOrFail(update, "更新");
    }

    /**
     * 修改用户状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改用户状态")
    @MzLog(title = "用户管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@pms.hasPermission('system:user:update')")
    @PutMapping("/update/status")
    public R<Boolean> updateStatus(@Validated @RequestBody SysIdAndStatusReqVo idAndStatusReqVo) {
        boolean updateStatus = sysUserService.updateStatus(idAndStatusReqVo);
        return R.okOrFail(updateStatus, "修改");
    }


    /**
     * 修改登录记录
     *
     * @param sysUserLoginLogDto 登录记录Dto
     * @return 保存结果
     */
    @ApiOperation(value = "修改用户记录", tags = {"每次登录成功进行修改"})
    @Ignore
    @PutMapping("/update/login/log")
    public R<Boolean> updateLoginLog(@Validated @RequestBody SysUserLoginLogDto sysUserLoginLogDto) {
        boolean updateStatus = sysUserService.updateLoginLog(sysUserLoginLogDto);
        return R.okOrFail(updateStatus, "修改");
    }

    /**
     * 删除用户
     *
     * @param userIds 集合/数组
     * @return 删除结果
     */
    @ApiOperation("删除用户")
    @MzLog(title = "用户管理", businessType = BusinessType.REMOVE)
    @PreAuthorize("@pms.hasPermission('system:user:delete')")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody @Validated @Size(min = 1) Long[] userIds) {
        boolean remove = sysUserService.removeUserByIds(Arrays.asList(userIds));
        return R.okOrFail(remove, "删除");
    }

}
