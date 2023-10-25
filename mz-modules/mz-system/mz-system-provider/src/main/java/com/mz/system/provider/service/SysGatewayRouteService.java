package com.mz.system.provider.service;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JavaType;
import com.mz.common.gateway.entity.MzFilterDefinition;
import com.mz.common.gateway.entity.MzGatewayRoute;
import com.mz.common.gateway.entity.MzPredicateDefinition;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.utils.MzJacksonUtils;
import com.mz.system.model.entity.SysGatewayRouteEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 网关路由表
 *
 * @author 小政同学 it_xiaozheng@163.com
 * @email 1911298402@qq.com
 * @date 2023-09-25 19:31:07
 */
public interface SysGatewayRouteService extends IService<SysGatewayRouteEntity> {

    PageUtils<SysGatewayRouteEntity> queryPage(SysGatewayRouteEntity sysGatewayRoute, Map<String, Object> params);

    boolean saveGatewayRoute(MzGatewayRoute gatewayRoute);

    boolean updateGatewayRouteById(MzGatewayRoute gatewayRoute);

    boolean resetRoute();

    boolean saveOrUpdateGatewayRoute(List<MzGatewayRoute> gatewayRoutes);

    MzGatewayRoute getGatewayRouteById(String routeId);


    @NotNull
    default  SysGatewayRouteEntity getSysGatewayRouteEntity(MzGatewayRoute gatewayRoute) {
        SysGatewayRouteEntity sysGatewayRoute = new SysGatewayRouteEntity();
        sysGatewayRoute.setRouteId(gatewayRoute.getRouteId());
        sysGatewayRoute.setRouteName(gatewayRoute.getRouteName());
        sysGatewayRoute.setRouteUri(gatewayRoute.getRouteUri());
        sysGatewayRoute.setRemark(gatewayRoute.getRemark());
        sysGatewayRoute.setMetadata(MzJacksonUtils.toJson(gatewayRoute.getMetadata()));
        sysGatewayRoute.setPredicates(MzJacksonUtils.toJson(gatewayRoute.getPredicates()));
        sysGatewayRoute.setFilters(MzJacksonUtils.toJson(gatewayRoute.getFilters()));
        sysGatewayRoute.setOrderNum(gatewayRoute.getOrder());
        sysGatewayRoute.setStatus(gatewayRoute.getStatus());
        return sysGatewayRoute;
    }

    @NotNull
    default MzGatewayRoute getMzGatewayRoute(SysGatewayRouteEntity gatewayRoute) {
        MzGatewayRoute mzGatewayRoute = new MzGatewayRoute();
        BeanUtil.copyProperties(gatewayRoute,mzGatewayRoute,"metadata","predicates","filters");
        mzGatewayRoute.setRouteId(gatewayRoute.getRouteId());
        mzGatewayRoute.setRouteName(gatewayRoute.getRouteName());
        mzGatewayRoute.setRouteUri(gatewayRoute.getRouteUri());
        mzGatewayRoute.setRemark(gatewayRoute.getRemark());
        mzGatewayRoute.setMetadata(MzJacksonUtils.toObj(gatewayRoute.getMetadata(),Map.class));
        String predicates = gatewayRoute.getPredicates();
        JavaType predicateType = MzJacksonUtils.getTypeFactory().constructParametricType(ArrayList.class, MzPredicateDefinition.class);
        mzGatewayRoute.setPredicates(MzJacksonUtils.toObj(predicates, predicateType));
        String filters = gatewayRoute.getFilters();
        JavaType filterType = MzJacksonUtils.getTypeFactory().constructParametricType(ArrayList.class, MzFilterDefinition.class);
        mzGatewayRoute.setFilters(MzJacksonUtils.toObj(filters,filterType));
        mzGatewayRoute.setOrder(gatewayRoute.getOrderNum());
        mzGatewayRoute.setStatus(gatewayRoute.getStatus());
        return mzGatewayRoute;
    }

    List<MzGatewayRoute> listGatewayRoute();
}

