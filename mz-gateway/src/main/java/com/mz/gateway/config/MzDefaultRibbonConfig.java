package com.mz.gateway.config;


import com.mz.gateway.ribbon.MzNacosRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * What -- Nz默认Ribbon配置
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzDefaultRibbonConfig
 * @CreateTime 2022/10/22 19:30
 */
@Configuration
@RibbonClients(
        value = {
                @RibbonClient(name = "mz-gateway", configuration = MzNacosRule.class),
        },
        defaultConfiguration = MzDefaultRibbonConfig.class)
public class MzDefaultRibbonConfig {

    @Bean
    public IRule iRule(){
        return new MzNacosRule();
    }

}
