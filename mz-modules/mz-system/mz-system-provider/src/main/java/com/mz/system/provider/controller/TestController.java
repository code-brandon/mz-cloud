package com.mz.system.provider.controller;



import cn.hutool.core.date.DateUtil;
import com.mz.common.security.annotation.Ignore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * What -- 资源服务器测试 控制器
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.provider.controller
 * @ClassName: UserController
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/29 18:18
 */
@RestController
@RequestMapping("/test")
@Api("资源服务器测试")
public class TestController {

    @ApiOperation("获取用户名")
    @GetMapping("/getuser")
    public Object getUser(Authentication authentication) {
        return authentication.getPrincipal();
    }

    @Ignore
    @ApiOperation( value = "测试Api忽略",tags = {"不进行授权"})
    @GetMapping("/getIsIgnore")
    public String getIsIgnore() {
        return DateUtil.formatDateTime(new Date());
    }
}
