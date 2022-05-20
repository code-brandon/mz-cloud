package com.mz.gateway.provider.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * What -- 动态路由服务事件
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzDynamicRouteServiceEvent
 * @CreateTime 2022/5/20 21:08
 */
@Slf4j
@Service
public class MzDynamicRouteServiceEvent implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;


    /**
     * 添加路由实体类
     * @param definition
     * @return
     */
    public boolean add(RouteDefinition definition){
        routeDefinitionWriter.save((Mono<RouteDefinition>) Mono.just(definition).subscribe());
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     *
     * @param definition 路由实体类
     * @return
     */
    public boolean update(RouteDefinition definition){
        try {
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
        }catch (Exception e){
            log.error("update 失败。没有找到对应的路由ID :{}",definition.getId());
        }

        routeDefinitionWriter.save((Mono<RouteDefinition>) (Mono.just(definition)).subscribe());
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     * 根据路由ID删除路由信息
     * @param id
     * @return
     */
    public boolean del(String id){
        routeDefinitionWriter.delete(Mono.just(id));
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }


    /**
     * 设置应用程序事件发布者
     * @param applicationEventPublisher
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 刷新路由表
     */
    public void refreshRoutes() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}