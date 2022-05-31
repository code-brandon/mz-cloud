package com.mz.system.provider.controller;



import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getuser")
    public Object getUser(Authentication authentication) {
        return authentication.getPrincipal();
    }
}
