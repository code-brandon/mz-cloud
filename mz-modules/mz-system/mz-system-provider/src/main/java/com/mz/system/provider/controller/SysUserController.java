package com.mz.system.provider.controller;

import com.mz.common.core.entity.R;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.security.annotation.Ignore;
import com.mz.common.security.entity.MzUserDetailsSecurity;
import com.mz.common.security.utils.MzSecurityUtils;
import com.mz.system.model.dto.SysUserDto;
import com.mz.system.model.entity.SysUserEntity;
import com.mz.system.model.vo.req.SysUserIdAndPasswdReqVo;
import com.mz.system.model.vo.res.SysUserResVo;
import com.mz.system.provider.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

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
    @PreAuthorize("@pms.hasPermission('system:user:query')")
    @PostMapping("/page")
    public R<PageUtils<SysUserResVo>> page(@RequestParam Map<String, Object> params, @RequestBody SysUserResVo userReqVo) {
        PageUtils<SysUserResVo> page = sysUserService.queryPage(params, userReqVo);
        return R.ok(page);
    }

    @ApiOperation("获取用户名")
    @GetMapping("/getUser")
    public R<MzUserDetailsSecurity> getUser() {
        return R.ok(MzSecurityUtils.getMzSysUserSecurity());
    }


    @ApiOperation("重置用户密码")
    @PutMapping("/resetPasswd")
    public R<Boolean> resetPasswd(@Valid @RequestBody SysUserIdAndPasswdReqVo userVo) {
        boolean b = sysUserService.resetPasswd(userVo);
        return b ? R.ok("重置成功!",Boolean.TRUE): R.fail("重置失败",Boolean.TRUE);
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
    public R<SysUserResVo> info(@PathVariable("userId") Long userId) {
        SysUserResVo sysUser = sysUserService.getUserById(userId);

        return R.ok(sysUser);
    }

    /**
     * 按用户名获取用户信息 (登录暴漏接口)
     *
     * @param userName 用户名
     * @return 单条数据
     */
    @ApiOperation("按用户名获取用户信息")
    @PostMapping("/getUserInfoByUserName")
    @Ignore
    public R<SysUserDto> loadUserByUserName(@Valid @RequestParam(value = "username") @NotBlank String userName) {
        SysUserDto sysUserDto = sysUserService.loadUserByUserName(userName);
        return R.ok(sysUserDto);
    }

    /**
     * 保存数据
     *
     * @param sysUserResVo 实体对象
     * @return 新增结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "sysUser 实体对象", dataTypeClass = SysUserEntity.class, paramType = "body", example = "{'name':'zahngsan'}")
    })
    @ApiOperation("保存数据")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SysUserResVo sysUserResVo) {
        boolean b = sysUserService.saveUser(sysUserResVo);
        return R.ok(b);
    }

    /**
     * 修改数据
     *
     * @param sysUserResVo 实体对象
     * @return 修改结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "sysUser 实体对象", dataTypeClass = SysUserEntity.class, paramType = "body", example = "{'name':'zahngsan'}")
    })
    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R<Boolean> update(@Valid @RequestBody SysUserResVo sysUserResVo) {
        boolean b = sysUserService.updateUserById(sysUserResVo);

        return R.ok(b);
    }

    /**
     * 删除数据
     *
     * @param userIds 集合/数组
     * @return 删除结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="userIds",value="userIds 数组对象", dataTypeClass = Long[].class, paramType = "body", example="['1','2']")
    })
    @ApiOperation("删除数据")
    @DeleteMapping("/delete")
    public R<Boolean> delete(@RequestBody Long[] userIds) {
        sysUserService.removeUserByIds(Arrays.asList(userIds));

        return R.ok(Boolean.TRUE);
    }

}
