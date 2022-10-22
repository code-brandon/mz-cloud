package com.mz.gateway.ribbon;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.mz.common.core.context.MzDefaultContextHolder;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * What -- 自定义MzNacosRule
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzNacosRule
 * @CreateTime 2022/10/22 19:07
 */
public class MzNacosRule extends AbstractLoadBalancerRule {

	private static final Logger LOGGER = LoggerFactory.getLogger(MzNacosRule.class);

	@Autowired
	private NacosDiscoveryProperties nacosDiscoveryProperties;

	@Autowired
	private NacosServiceManager nacosServiceManager;

	@Override
	public Server choose(Object key) {
		try {
			// 集群名称
			String clusterName = this.nacosDiscoveryProperties.getClusterName();

			// 集群分组
			String group = this.nacosDiscoveryProperties.getGroup();

			// 负载均衡器
			DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();

			// 均衡器名称
			String name = loadBalancer.getName();

			// 根据名称获取服务
			NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());

			// 根据均衡器名称，集群分组 获取实例
			List<Instance> instances = namingService.selectInstances(name, group, true);
			if (CollectionUtils.isEmpty(instances)) {
				LOGGER.warn("no instance in service {}", name);
				return null;
			}

			List<String> envs = (List<String>) MzDefaultContextHolder.CONTEXT_HOLDER.get().get("env");
			List<Instance> instancesToChoose = instances;
			if (StringUtils.isNotBlank(clusterName)) {
				List<Instance> sameClusterInstances = instances.stream()
						.filter(instance -> Objects.equals(clusterName,
								instance.getClusterName()))
						.collect(Collectors.toList());
				if (!CollectionUtils.isEmpty(sameClusterInstances)) {
					instancesToChoose = sameClusterInstances;

					if (!CollectionUtils.isEmpty(envs)) {
						Map<String, List<Instance>> envInstanceMap = sameClusterInstances.stream().filter(f -> {
							return !MapUtils.isEmpty(f.getMetadata()) && Objects.nonNull(f.getMetadata().get("env"));
						}).collect(Collectors.groupingBy(b -> {
							return b.getMetadata().get("env");
						}));
						LOGGER.warn("环境实例分组：{}", JSON.toJSONString(envInstanceMap));

						instancesToChoose = MapUtils.isNotEmpty(envInstanceMap) ? envInstanceMap.get((envs).get(0)) : sameClusterInstances;
					}

				}
				else {
					LOGGER.warn(
							"A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}",
							name, clusterName, instances);
				}
			}

			Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToChoose);

			return new NacosServer(instance);
		}
		catch (Exception e) {
			LOGGER.warn("MzNacosRule error", e);
			return null;
		}
	}

	/**
	 *
	 * @param iClientConfig
	 */
	@Override
	public void initWithNiwsConfig(IClientConfig iClientConfig) {
	}

}