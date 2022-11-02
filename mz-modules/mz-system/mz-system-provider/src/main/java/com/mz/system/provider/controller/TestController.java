package com.mz.system.provider.controller;


import cn.hutool.core.date.DateUtil;
import com.mz.common.redis.annotation.MzLock;
import com.mz.common.security.annotation.Ignore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Slf4j
@RestController
@RequestMapping("/test")
@Api("资源服务器测试")
public class TestController {

    private Integer count = 4000;
    private Integer initNum = 4000;

    @Ignore
    @ApiOperation( value = "测试Api忽略",tags = {"不进行授权"})
    @GetMapping("/getIsIgnore")
    @MzLock(lockKey = "getIsIgnore",waitTime = 15)
    public String getIsIgnore() {
        log.warn("count 剩余：" + count);
        count--;
        return DateUtil.formatDateTime(new Date());
    }

    @Ignore
    @ApiOperation( value = "测试Api重置计数",tags = {"不进行授权"})
    @GetMapping("/resetIgnoreCount")
    public String resetIgnoreCount() {
        count = initNum;
        log.warn("count 重置：" + count);
        return "成功！";
    }

    @Ignore
    @ApiOperation(value = "测试Api初始计数", tags = {"不进行授权"})
    @GetMapping("/setIgnoreCount/{initNum}")
    public String setIgnoreCount(@PathVariable("initNum") Integer initNum) {
        this.initNum = initNum;
        this.count = initNum;
        log.warn("count 初始：" + count);
        return "成功！";
    }
}
