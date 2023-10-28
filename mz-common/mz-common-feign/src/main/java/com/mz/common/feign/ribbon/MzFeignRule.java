package com.mz.common.feign.ribbon;

import cn.hutool.json.JSONUtil;
import com.google.common.base.Optional;
import com.mz.common.constant.MzConstant;
import com.mz.common.feign.context.MzFeignContextHolder;
import com.mz.common.utils.MzJacksonUtils;
import com.mz.common.utils.MzUtils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * What -- 自定义 ZoneAvoidanceRule
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzNacosRule
 * @CreateTime 2022/10/22 19:07
 */
@Slf4j
public class MzFeignRule extends ZoneAvoidanceRule {

	public MzFeignRule() {
		super();
	}

	@Override
	public AbstractServerPredicate getPredicate() {
		return super.getPredicate();
	}

	/**
	 * 选择 服务器
	 * @param key
	 * @return
	 */
	@Override
	public Server choose(Object key) {
		List<Server> servers = null;
		ILoadBalancer loadBalancer = getLoadBalancer();
		List<Server> reachableServers = loadBalancer.getReachableServers();
		// 对服务器  根据Nacos元数据 中规定的 env 进行分组
		Map<String, List<Server>> serverGroup = reachableServers.stream().filter(f -> Optional.fromNullable(JSONUtil.parseObj(f).getJSONObject("metadata")).toJavaUtil().map(m -> m.getStr(MzConstant.GATEWAY_ENV)).isPresent()).collect(Collectors.groupingBy(b -> JSONUtil.parseObj(b).getJSONObject("metadata").getStr(MzConstant.GATEWAY_ENV)));

		// MzFeign 上下问中获取参数
		Map<String, Object> contextMap = MzFeignContextHolder.CONTEXT_HOLDER.get();
		// 当前环境
		String env = (String) contextMap.get(MzConstant.GATEWAY_ENV);
		// 如果分组后为空则不执行
		if (MzUtils.notEmpty(serverGroup)){
			// 从请求携带的环境中选择
            servers = serverGroup.get(env);
        }

		log.debug("env: {} \nserverGroup: {} \nservers: {} \nreachableServers :{} ",env, MzJacksonUtils.toJson(serverGroup), MzJacksonUtils.toJson(servers),MzJacksonUtils.toJson(reachableServers));
		// 当选择的服务器列表为空时，走默认服务器列表 交由ZoneAvoidanceRule默认选择处理
		Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(Optional.fromNullable(servers).or(loadBalancer.getAllServers()), key);
		if (server.isPresent()) {
			return server.get();
		} else {
			return null;
		}
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
		log.debug("初始化配置：{}",clientConfig.getClientName());
		super.initWithNiwsConfig(clientConfig);
	}
}