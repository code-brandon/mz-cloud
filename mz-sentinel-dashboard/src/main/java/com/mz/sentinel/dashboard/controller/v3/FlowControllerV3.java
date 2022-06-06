/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mz.sentinel.dashboard.controller.v3;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.mz.sentinel.dashboard.auth.AuthAction;
import com.mz.sentinel.dashboard.auth.AuthService.PrivilegeType;
import com.mz.sentinel.dashboard.client.CommandFailedException;
import com.mz.sentinel.dashboard.client.SentinelApiClient;
import com.mz.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.mz.sentinel.dashboard.discovery.MachineInfo;
import com.mz.sentinel.dashboard.domain.Result;
import com.mz.sentinel.dashboard.repository.rule.InMemoryRuleRepositoryAdapter;
import com.mz.sentinel.dashboard.rule.DynamicRuleProvider;
import com.mz.sentinel.dashboard.rule.DynamicRulePublisher;
import com.mz.sentinel.dashboard.util.AsyncUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * What -- Flow rule controller (v3).
 * <br>
 * Describe -- redis 持久化
 * <br>
 * TODO 暂时将路径映射改为 v1
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: FlowControllerV3
 * @CreateTime 2022/6/5 15:34
 */
@RestController
@RequestMapping(value = "/v1/flow")
public class FlowControllerV3 {

    private final Logger logger = LoggerFactory.getLogger(FlowControllerV3.class);

    @Autowired
    private InMemoryRuleRepositoryAdapter<FlowRuleEntity> repository;

    @Autowired
    private SentinelApiClient sentinelApiClient;

    @Autowired
    @Qualifier("flowRuleRedisProvider")
    private DynamicRuleProvider<List<FlowRuleEntity>> ruleProvider;
    @Autowired
    @Qualifier("flowRuleRedisPublisher")
    private DynamicRulePublisher<List<FlowRuleEntity>> rulePublisher;

    @GetMapping("/rules")
    @AuthAction(PrivilegeType.READ_RULE)
    public Result<List<FlowRuleEntity>> apiQueryMachineRules(@RequestParam String app) {

        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
        try {
            List<FlowRuleEntity> rules = ruleProvider.getRules(app);
            if (rules != null && !rules.isEmpty()) {
                for (FlowRuleEntity entity : rules) {
                    entity.setApp(app);
                    if (entity.getClusterConfig() != null && entity.getClusterConfig().getFlowId() != null) {
                        entity.setId(entity.getClusterConfig().getFlowId());
                    }
                }
            }
            rules = repository.saveAll(rules);
            return Result.ofSuccess(rules);
        } catch (Throwable throwable) {
            logger.error("Error when querying flow rules", throwable);
            return Result.ofThrowable(-1, throwable);
        }
    }

    private <R> Result<R> checkEntityInternal(FlowRuleEntity entity) {
        if (entity == null) {
            return Result.ofFail(-1, "invalid body");
        }
        if (StringUtil.isBlank(entity.getApp())) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
        if (StringUtil.isBlank(entity.getLimitApp())) {
            return Result.ofFail(-1, "limitApp can't be null or empty");
        }
        if (StringUtil.isBlank(entity.getResource())) {
            return Result.ofFail(-1, "resource can't be null or empty");
        }
        if (entity.getGrade() == null) {
            return Result.ofFail(-1, "grade can't be null");
        }
        if (entity.getGrade() != 0 && entity.getGrade() != 1) {
            return Result.ofFail(-1, "grade must be 0 or 1, but " + entity.getGrade() + " got");
        }
        if (entity.getCount() == null || entity.getCount() < 0) {
            return Result.ofFail(-1, "count should be at lease zero");
        }
        if (entity.getStrategy() == null) {
            return Result.ofFail(-1, "strategy can't be null");
        }
        if (entity.getStrategy() != 0 && StringUtil.isBlank(entity.getRefResource())) {
            return Result.ofFail(-1, "refResource can't be null or empty when strategy!=0");
        }
        if (entity.getControlBehavior() == null) {
            return Result.ofFail(-1, "controlBehavior can't be null");
        }
        int controlBehavior = entity.getControlBehavior();
        if (controlBehavior == 1 && entity.getWarmUpPeriodSec() == null) {
            return Result.ofFail(-1, "warmUpPeriodSec can't be null when controlBehavior==1");
        }
        if (controlBehavior == 2 && entity.getMaxQueueingTimeMs() == null) {
            return Result.ofFail(-1, "maxQueueingTimeMs can't be null when controlBehavior==2");
        }
        if (entity.isClusterMode() && entity.getClusterConfig() == null) {
            return Result.ofFail(-1, "cluster config should be valid");
        }
        return null;
    }

    @PostMapping("/rule")
    @AuthAction(value = PrivilegeType.WRITE_RULE)
    public Result<FlowRuleEntity> apiAddFlowRule(@RequestBody FlowRuleEntity entity) {

        Result<FlowRuleEntity> checkResult = checkEntityInternal(entity);
        if (checkResult != null) {
            return checkResult;
        }
        entity.setId(null);
        Date date = new Date();
        entity.setGmtCreate(date);
        entity.setGmtModified(date);
        entity.setLimitApp(entity.getLimitApp().trim());
        entity.setResource(entity.getResource().trim());
        try {
            entity = repository.save(entity);
            publishRules(entity.getApp(), entity.getIp(), entity.getPort());
        } catch (Throwable throwable) {
            logger.error("Failed to add flow rule", throwable);
            return Result.ofThrowable(-1, throwable);
        }
        return Result.ofSuccess(entity);
    }

    @PutMapping("/rule/{id}")
    @AuthAction(PrivilegeType.WRITE_RULE)
    public Result<FlowRuleEntity> apiUpdateFlowRule(@PathVariable("id") Long id,
                                                    @RequestBody FlowRuleEntity entity) {
        if (id == null || id <= 0) {
            return Result.ofFail(-1, "Invalid id");
        }
        entity = repository.findById(id);
        if (entity == null) {
            return Result.ofFail(-1, "id " + id + " does not exist");
        }

        entity.setApp(entity.getApp());
        entity.setIp(entity.getIp());
        entity.setPort(entity.getPort());
        Result<FlowRuleEntity> checkResult = checkEntityInternal(entity);
        if (checkResult != null) {
            return checkResult;
        }

        entity.setId(id);
        Date date = new Date();
        entity.setGmtCreate(entity.getGmtCreate());
        entity.setGmtModified(date);
        try {
            entity = repository.save(entity);
            if (entity == null) {
                return Result.ofFail(-1, "save entity fail");
            }
            publishRules(entity.getApp(), entity.getIp(), entity.getPort());
        } catch (Throwable throwable) {
            logger.error("Failed to update flow rule", throwable);
            return Result.ofThrowable(-1, throwable);
        }
        return Result.ofSuccess(entity);
    }

    @DeleteMapping("/rule/{id}")
    @AuthAction(PrivilegeType.DELETE_RULE)
    public Result<Long> apiDeleteRule(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return Result.ofFail(-1, "Invalid id");
        }
        FlowRuleEntity entity = repository.findById(id);
        if (entity == null) {
            return Result.ofSuccess(null);
        }

        try {
            repository.delete(id);
            publishRules(entity.getApp(), entity.getIp(), entity.getPort());
        } catch (Exception e) {
            return Result.ofFail(-1, e.getMessage());
        }
        return Result.ofSuccess(id);
    }

    @PutMapping("/save.json")
    @AuthAction(PrivilegeType.WRITE_RULE)
    public Result<FlowRuleEntity> apiUpdateFlowRule(Long id, String app,
                                                    String limitApp, String resource, Integer grade,
                                                    Double count, Integer strategy, String refResource,
                                                    Integer controlBehavior, Integer warmUpPeriodSec,
                                                    Integer maxQueueingTimeMs) {
        if (id == null) {
            return Result.ofFail(-1, "id can't be null");
        }
        FlowRuleEntity entity = repository.findById(id);
        if (entity == null) {
            return Result.ofFail(-1, "id " + id + " dose not exist");
        }
        if (StringUtil.isNotBlank(app)) {
            entity.setApp(app.trim());
        }
        if (StringUtil.isNotBlank(limitApp)) {
            entity.setLimitApp(limitApp.trim());
        }
        if (StringUtil.isNotBlank(resource)) {
            entity.setResource(resource.trim());
        }
        if (grade != null) {
            if (grade != 0 && grade != 1) {
                return Result.ofFail(-1, "grade must be 0 or 1, but " + grade + " got");
            }
            entity.setGrade(grade);
        }
        if (count != null) {
            entity.setCount(count);
        }
        if (strategy != null) {
            if (strategy != 0 && strategy != 1 && strategy != 2) {
                return Result.ofFail(-1, "strategy must be in [0, 1, 2], but " + strategy + " got");
            }
            entity.setStrategy(strategy);
            if (strategy != 0) {
                if (StringUtil.isBlank(refResource)) {
                    return Result.ofFail(-1, "refResource can't be null or empty when strategy!=0");
                }
                entity.setRefResource(refResource.trim());
            }
        }
        if (controlBehavior != null) {
            if (controlBehavior != 0 && controlBehavior != 1 && controlBehavior != 2) {
                return Result.ofFail(-1, "controlBehavior must be in [0, 1, 2], but " + controlBehavior + " got");
            }
            if (controlBehavior == 1 && warmUpPeriodSec == null) {
                return Result.ofFail(-1, "warmUpPeriodSec can't be null when controlBehavior==1");
            }
            if (controlBehavior == 2 && maxQueueingTimeMs == null) {
                return Result.ofFail(-1, "maxQueueingTimeMs can't be null when controlBehavior==2");
            }
            entity.setControlBehavior(controlBehavior);
            if (warmUpPeriodSec != null) {
                entity.setWarmUpPeriodSec(warmUpPeriodSec);
            }
            if (maxQueueingTimeMs != null) {
                entity.setMaxQueueingTimeMs(maxQueueingTimeMs);
            }
        }
        Date date = new Date();
        entity.setGmtModified(date);
        try {
            entity = repository.save(entity);
            if (entity == null) {
                return Result.ofFail(-1, "save entity fail: null");
            }

            publishRules(entity.getApp(), entity.getIp(), entity.getPort()).get(15000, TimeUnit.MILLISECONDS);
            return Result.ofSuccess(entity);
        } catch (Throwable t) {
            Throwable e = t instanceof ExecutionException ? t.getCause() : t;
            logger.error("Error when updating flow rules, app={}, ip={}, ruleId={}", entity.getApp(),
                    entity.getIp(), id, e);
            return Result.ofFail(-1, e.getMessage());
        }
    }

    @DeleteMapping("/delete.json")
    @AuthAction(PrivilegeType.WRITE_RULE)
    public Result<Long> apiDeleteFlowRule(Long id) {

        if (id == null) {
            return Result.ofFail(-1, "id can't be null");
        }
        FlowRuleEntity entity = repository.findById(id);
        if (entity == null) {
            return Result.ofSuccess(null);
        }

        try {
            repository.delete(id);
        } catch (Exception e) {
            return Result.ofFail(-1, e.getMessage());
        }
        try {
            publishRules(entity.getApp(), entity.getIp(), entity.getPort()).get(5000, TimeUnit.MILLISECONDS);
            return Result.ofSuccess(id);
        } catch (Throwable t) {
            Throwable e = t instanceof ExecutionException ? t.getCause() : t;
            logger.error("Error when deleting flow rules, app={}, ip={}, id={}", entity.getApp(),
                    entity.getIp(), id, e);
            return Result.ofFail(-1, e.getMessage());
        }
    }

    // old publishRules
    private CompletableFuture<Void> publishRules_back(String app, String ip, Integer port) {
        List<FlowRuleEntity> rules = repository.findAllByMachine(MachineInfo.of(app, ip, port));
        return sentinelApiClient.setFlowRuleOfMachineAsync(app, ip, port, rules);
    }
    //修改publishRules
    private CompletableFuture<Void> publishRules(String app, String ip, Integer port)
    {
        List<FlowRuleEntity> rules = repository.findAllByApp(app);
        try {
            //修改了redis中的存储及发布修改的规则，客户端app订阅channel后将规则保存在本地内存中
            rulePublisher.publish(app, rules);
            logger.info("添加{},限流规则成功{}",app, JSON.toJSONString(rules));
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("publishRules failed", e);
            return AsyncUtils.newFailedFuture(new CommandFailedException("Sentinel 推送规则到Redis失败"));
        }
        //核心代码，sentinel-dashboard通过http的形式进行数据推送，客户端接收后将规则保存在本地内存中
        return sentinelApiClient.setFlowRuleOfMachineAsync(app, ip, port, rules);
    }
}