package com.mz.system.provider.controller;


import cn.hutool.core.date.DateUtil;
import com.mz.common.core.entity.R;
import com.mz.common.security.annotation.Ignore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
@Controller
@RequestMapping("/test")
@Api(tags = "资源服务器测试")
public class TestController {

    private Integer count = 4000;
    private Integer initNum = 4000;

    @Ignore(false)
    @ApiOperation( value = "测试Api忽略",notes ="不进行授权")
    @ResponseBody
    @RequestMapping("/getIsIgnore")
    // @MzLock(lockKey = "getIsIgnore",waitTime = 15)
    public R<Map<String, Object>> getIsIgnore() {
        count--;
        log.warn("count 剩余：" + count);
        Map<String, Object> mp = new HashMap<>();
        mp.put("time", DateUtil.formatDateTime(new Date()));
        mp.put("count", count);
        return R.ok(mp);
    }

    @Ignore
    @ApiOperation(value = "测试Api剩余数量", notes = "不进行授权")
    @ResponseBody
    @GetMapping("/getCount")
    public R<Map<String, Object>> getCount() {
        log.warn("count 剩余：" + count);
        Map<String, Object> mp = new HashMap<>();
        mp.put("time", DateUtil.formatDateTime(new Date()));
        mp.put("count", count);
        return R.ok(mp);
    }

    @Ignore
    @ApiOperation( value = "测试Api重置计数",notes ="不进行授权")
    @ResponseBody
    @GetMapping("/resetIgnoreCount")
    public R<String> resetIgnoreCount() {
        count = initNum;
        log.warn("count 重置：" + count);
        return R.ok("成功！");
    }

    @Ignore
    @ApiOperation(value = "测试Api初始计数", notes ="不进行授权")
    @ResponseBody
    @GetMapping("/setIgnoreCount/{initNum}")
    public R<String> setIgnoreCount(@PathVariable("initNum") Integer initNum) {
        this.initNum = initNum;
        this.count = initNum;
        log.warn("count 初始：" + count);
        return R.ok("成功！");
    }
}
