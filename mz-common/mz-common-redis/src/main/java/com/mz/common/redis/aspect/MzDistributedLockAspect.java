package com.mz.common.redis.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.common.redis.annotation.MzLock;
import com.mz.common.redis.constants.RedisConstant;
import com.mz.common.redis.exception.MzRedisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * What -- Mz分布式锁 切面
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzDistributedLockAspect
 * @CreateTime 2022/10/28 21:37
 */
@Aspect
@Slf4j
@ConditionalOnClass({Redisson.class, RedisOperations.class})
@RequiredArgsConstructor
public class MzDistributedLockAspect {

    private final Redisson redisson;
 
    @Around("@annotation(com.mz.common.redis.annotation.MzLock)")
    public Object doLock(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        ObjectMapper objectMapper = new ObjectMapper();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        MzLock distributedLock = method.getAnnotation(MzLock.class);

        // 获取锁的名称
        String lockKey = distributedLock.lockKey();
        //获取方法的形参名数组，spring常用，rpc时不需要，反射也不需要。表达式中写形参名
        LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = nameDiscoverer.getParameterNames(method);

        String finalKey = RedisConstant.MZ_LOCK_KEY;
        // 如果设置了 存储Key则使用存储Key
        if (!StringUtils.isEmpty(distributedLock.storageKey())) {
            finalKey = distributedLock.storageKey();
        }

        Map<String, Object> hashMap = new HashMap<>(5);
        if (parameterNames != null && parameterNames.length > 0) {
            //spel表达式解析
            ExpressionParser parser = new SpelExpressionParser();
            EvaluationContext context = new StandardEvaluationContext();
            Expression expression = parser.parseExpression(lockKey);
            //存储形参和传值的映射
            //获取方法传入的参数值
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < parameterNames.length; i++) {
                //建立形参名和值的映射关系，存入上下文当中，后面取出表达式带有的形参
                context.setVariable(parameterNames[i], args[i]);
                hashMap.put(parameterNames[i], args[i]);
            }
            //这里取字符串类型作为key
            String newKey = expression.getValue(context, String.class);
            finalKey += newKey;
        } else {
            finalKey += lockKey;
        }
        log.info("DistributedLock finalKey=[{}]开始上锁,参数为:{}", finalKey, objectMapper.writeValueAsString(hashMap));
        RLock lock = lock(finalKey, distributedLock);
        try {
            Object result = joinPoint.proceed();
            log.info("DistributedLock finalKey=[{}],方法运行结束,返回值为:{}", finalKey, objectMapper.writeValueAsString(result));
            return result;
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
            long finallyTime = System.currentTimeMillis() - startTime;
            log.info(String.format("\033[%dm%s\033[0m", 33, "释放redis锁，处理耗时：{}毫秒，{}秒，{}分钟"), finallyTime, finallyTime / 1000.0, String.format("%.3f", finallyTime / 1000.0 / 60.0));
        }
    }


    private RLock lock(String lockName, MzLock distributedLock) throws Exception {
        long leaseTime = distributedLock.leaseTime();
        TimeUnit timeUnit = distributedLock.timeUnit();
        long waitTime = distributedLock.waitTime();
        String message = distributedLock.message();
        RLock lock = redisson.getLock(lockName);
        // 上锁
        if (distributedLock.tryLock()) {
            if (!lock.tryLock(waitTime, leaseTime, timeUnit)) {
                // 获取锁失败
                throw new MzRedisException(message);
            }
        } else {
            if (leaseTime > 0) {
                lock.lock(leaseTime, timeUnit);
            } else {
                lock.lock();
            }
        }
        return lock;
    }
}