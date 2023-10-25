package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.constant.MzConstant;
import com.mz.common.gateway.entity.MzGatewayRoute;
import com.mz.common.gateway.utils.MzRouteNameUtils;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.common.redis.utils.MzRedisUtil;
import com.mz.common.utils.JsonFormatUtils;
import com.mz.common.utils.MzJacksonUtils;
import com.mz.system.model.entity.SysGatewayRouteEntity;
import com.mz.system.provider.dao.SysGatewayRouteDao;
import com.mz.system.provider.service.SysGatewayRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 网关路由表实现
 *
 * @author 小政同学 it_xiaozheng@163.com
 * @email 1911298402@qq.com
 * @date 2023-09-25 19:31:07
 */
@Service("sysGatewayRouteService")
@Slf4j
@RequiredArgsConstructor
public class SysGatewayRouteServiceImpl extends ServiceImpl<SysGatewayRouteDao, SysGatewayRouteEntity> implements SysGatewayRouteService {

    private final MzRedisUtil mzRedisUtil;

    @Override
    public PageUtils<SysGatewayRouteEntity> queryPage(SysGatewayRouteEntity sysGatewayRoute, Map<String, Object> params) {
        IPage<SysGatewayRouteEntity> page = this.page(
                new Query<SysGatewayRouteEntity>().getPage(params),
                Wrappers.query(sysGatewayRoute)
        );
        return new PageUtils<>(page);
    }

    @Override
    public boolean saveGatewayRoute(MzGatewayRoute gatewayRoute) {
        SysGatewayRouteEntity sysGatewayRoute = getSysGatewayRouteEntity(gatewayRoute);
        boolean save = save(sysGatewayRoute);
        if (save) {
            String routeJson = MzJacksonUtils.toJson(gatewayRoute);
            mzRedisUtil.hPut(MzConstant.ROUTE_KEY, sysGatewayRoute.getRouteId(), routeJson);
            pubRedisMsg();
        }
        return save;
    }

    @Override
    public boolean updateGatewayRouteById(MzGatewayRoute gatewayRoute) {
        SysGatewayRouteEntity sysGatewayRoute = getSysGatewayRouteEntity(gatewayRoute);
        boolean update = updateById(sysGatewayRoute);
        if (update) {
            SysGatewayRouteEntity gatewayRouteEntity = getById(sysGatewayRoute.getRouteId());
            MzGatewayRoute mzGatewayRoute = getMzGatewayRoute(gatewayRouteEntity);
            String routeJson = MzJacksonUtils.toJson(mzGatewayRoute);
            mzRedisUtil.hPut(MzConstant.ROUTE_KEY, sysGatewayRoute.getRouteId(), routeJson);
            pubRedisMsg();
        }
        return update;
    }

    /**
     * 重新加载网关路由
     * @return
     */
    @Override
    public boolean resetRoute() {
        List<MzGatewayRoute> gatewayRoutes = this.listGatewayRoute();
        Map<String, String> routeMap = gatewayRoutes.parallelStream().collect(Collectors.toMap(MzGatewayRoute::getRouteId, MzJacksonUtils::toJson, (k, v) -> v));
        mzRedisUtil.hPutAll(MzConstant.ROUTE_KEY, routeMap);
        pubRedisMsg();
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean saveOrUpdateGatewayRoute(List<MzGatewayRoute> gatewayRoutes) {
        MzRouteNameUtils.renameRouteArgs(gatewayRoutes);
        List<SysGatewayRouteEntity> sysGatewayRoutes = gatewayRoutes.parallelStream().map(this::getSysGatewayRouteEntity).collect(Collectors.toList());
        log.info("张三: \n{}", JsonFormatUtils.formatJson(MzJacksonUtils.toJson(sysGatewayRoutes)));
        return saveOrUpdateBatch(sysGatewayRoutes);
    }

    @Override
    public MzGatewayRoute getGatewayRouteById(String routeId) {
        SysGatewayRouteEntity gatewayRouteEntity = this.getById(routeId);
        return getMzGatewayRoute(gatewayRouteEntity);
    }

    @Override
    public List<MzGatewayRoute> listGatewayRoute() {
        return list().parallelStream().map(this::getMzGatewayRoute).collect(Collectors.toList());
    }

    /**
     * 推送路由更新
     */
    private void pubRedisMsg() {
        mzRedisUtil.convertAndSend(MzConstant.ROUTE_CHANNEL_KEY, "1");
    }

}