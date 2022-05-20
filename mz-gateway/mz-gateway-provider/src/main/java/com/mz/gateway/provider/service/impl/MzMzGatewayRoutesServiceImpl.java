package com.mz.gateway.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.mz.gateway.model.entity.MzGatewayRoutesEntity;
import com.mz.gateway.provider.service.MzGatewayRoutesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * What -- 网关路由服务实现
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzMzGatewayRoutesServiceImpl
 * @CreateTime 2022/5/20 21:15
 */
@Service
@Slf4j
public class MzMzGatewayRoutesServiceImpl implements MzGatewayRoutesService {

    public static final String GATEWAY_DEFINE_LIST_KEY = "gateway_routes_list_key";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    /**
     * 获取所有路由信息
     * @return
     * @throws Exception
     */
    @Override
    public List<MzGatewayRoutesEntity> findAll() throws Exception {

        Long size = redisTemplate.opsForList().size( GATEWAY_DEFINE_LIST_KEY );
        List<MzGatewayRoutesEntity> list = redisTemplate.opsForList().range( GATEWAY_DEFINE_LIST_KEY, 0, size );
        return list;
    }

    /**
     * 加载路由定义
     * @return
     */
    @Override
    public String loadRouteDefinition() {
        try {
            List <MzGatewayRoutesEntity> gatewayDefineServiceAll = findAll();
            if (gatewayDefineServiceAll == null) {
                return "none route defined";
            }
            for (MzGatewayRoutesEntity gatewayDefine : gatewayDefineServiceAll) {
                RouteDefinition definition = new RouteDefinition();
                definition.setId( gatewayDefine.getServiceId() );
                definition.setUri( new URI( gatewayDefine.getUri() ) );
                List <PredicateDefinition> predicateDefinitions = getPredicateDefinition(gatewayDefine.getPredicates());
                if (predicateDefinitions != null) {
                    definition.setPredicates( predicateDefinitions );
                }
                List <FilterDefinition> filterDefinitions = getFilterDefinition(gatewayDefine.getFilters());
                if (filterDefinitions != null) {
                    definition.setFilters( filterDefinitions );
                }
                routeDefinitionWriter.save( Mono.just( definition ) ).subscribe();
                publisher.publishEvent( new RefreshRoutesEvent( this ) );
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }

    /**
     * 获取所有的 自定义路由规则
     * @param gatewayDefine
     * @return
     * @throws Exception
     */
    @Override
    public MzGatewayRoutesEntity save(MzGatewayRoutesEntity gatewayDefine) throws Exception {
        log.info( "save RouteDefinition : {}",gatewayDefine );
        redisTemplate.opsForList().rightPush(  GATEWAY_DEFINE_LIST_KEY, gatewayDefine );
        return gatewayDefine;
    }

    /**
     * 根据ID删除路由信息
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteById(String id) throws Exception {
        List <MzGatewayRoutesEntity> all = findAll();
        for (MzGatewayRoutesEntity gatewayDefine : all) {
            if(gatewayDefine.getServiceId().equals( id )){
                redisTemplate.opsForList().remove( GATEWAY_DEFINE_LIST_KEY,0, gatewayDefine);
            }
        }
    }

    /**
     * 根据ID判断是否存在
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean existsById(String id) throws Exception {
        List <MzGatewayRoutesEntity> all = findAll();
        for (MzGatewayRoutesEntity gatewayDefine : all) {
            if(gatewayDefine.getServiceId().equals( id )){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PredicateDefinition> getPredicateDefinition(String predicates) {
        if ( !StringUtils.isEmpty( predicates )) {
            List<PredicateDefinition> predicateDefinitionList = JSON.parseArray(predicates, PredicateDefinition.class);
            return predicateDefinitionList;
        } else {
            return null;
        }
    }
    @Override
    public List<FilterDefinition> getFilterDefinition(String filters) {
        if (!StringUtils.isEmpty( filters )) {
            List<FilterDefinition> filterDefinitionList = JSON.parseArray(filters, FilterDefinition.class);
            return filterDefinitionList;
        } else {
            return null;
        }
    }
}