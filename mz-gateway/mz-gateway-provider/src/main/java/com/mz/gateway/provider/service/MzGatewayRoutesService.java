package com.mz.gateway.provider.service;

import com.mz.gateway.model.entity.MzGatewayRoutesEntity;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.util.List;

/**
 * What -- 网关路由服务接口
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzGatewayRoutesService
 * @CreateTime 2022/5/20 21:15
 */
public interface MzGatewayRoutesService {

    List<MzGatewayRoutesEntity> findAll() throws Exception;

    String loadRouteDefinition() throws Exception;

    MzGatewayRoutesEntity save(MzGatewayRoutesEntity gatewayDefine) throws Exception;

    void deleteById(String id) throws Exception;

    boolean existsById(String id)throws Exception;

    List<PredicateDefinition> getPredicateDefinition(String predicates) ;

    List<FilterDefinition> getFilterDefinition(String filters) ;

}