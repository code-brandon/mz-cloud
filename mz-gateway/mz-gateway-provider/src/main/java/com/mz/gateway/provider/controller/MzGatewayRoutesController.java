package com.mz.gateway.provider.controller;


import com.mz.common.core.utils.R;
import com.mz.gateway.provider.event.MzDynamicRouteServiceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.*;

/**
 * What -- 网关路由控制器
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzGatewayRoutesController
 * @CreateTime 2022/5/20 21:07
 */
@RestController
@RequestMapping("/gateway")
public class MzGatewayRoutesController {

    @Autowired
    private MzDynamicRouteServiceEvent mzDynamicRouteService;

    /**
     * 刷新路由信息
     * @return
     */
    @GetMapping("/refreshRoutes")
    public R refreshRoutes(){
        mzDynamicRouteService.refreshRoutes();
        return R.ok();
    }

    /**
     *
     * @param definition
     * @return
     */
    @RequestMapping(value = "routes/add",method = RequestMethod.POST)
    public R add(@RequestBody RouteDefinition definition){
        boolean flag = mzDynamicRouteService.add(definition);
        if(flag){
            return R.ok();
        }
        return R.fail();
    }


    /**
     *
     * @param definition
     * @return
     */
    @RequestMapping(value = "routes/update",method = RequestMethod.POST)
    public R update(@RequestBody RouteDefinition definition){
        boolean flag = mzDynamicRouteService.update(definition);
        if(flag){
            return R.ok();
        }
        return R.fail();
    }

    /**
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "routes/del",method = RequestMethod.POST)
    public R update(@RequestParam("serviceId") String serviceId){
        boolean flag = mzDynamicRouteService.del(serviceId);
        if(flag){
            return R.ok();
        }
        return R.fail();
    }
}