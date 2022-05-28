package com.mz.gateway.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesResultEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * What -- 路由刷新结果监听
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.gateway.listener
 * @ClassName: RefreshRoutesResultListener
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/5/25 18:12
 */
@Component
@Slf4j
public class RefreshRoutesResultListener implements
        ApplicationListener<RefreshRoutesResultEvent>{

    @Override
    public void onApplicationEvent(RefreshRoutesResultEvent event) {
        log.info("event = {} , 接收刷新路由结果事件" , event);
    }
}
