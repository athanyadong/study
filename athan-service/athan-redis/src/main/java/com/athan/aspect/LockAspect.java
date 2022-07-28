package com.athan.aspect;

import com.athan.annotation.Lock4j;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @aurhor yyh
 * @description redis注解的切入配置
 * @date 2022/7/27 14:49
 */
@Aspect
@AllArgsConstructor
@Component
@Slf4j
public class LockAspect {


    private final RedissonClient redissonClient;//Redisson 主接口，用于通过同步/异步接口访问所有 redisson 对象

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.athan.annotation.Lock4j)")
    public void lockPointCut() {
    }

    @Around(value = "lockPointCut()")//配置切入点
    public void around(ProceedingJoinPoint joinPoint) {//配置连接点
        //获取注解
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();//提供接口信息的访问
        if (null == method) {
            return;
        }
        //找到此注释
        Lock4j lock4j = method.getAnnotation(Lock4j.class);
        //获取他的参数
        String key = lock4j.key();
        long expire = lock4j.expire();
        long timeout = lock4j.timeout();
        log.info("redis锁注解获取到的信息为: key:{},过期时间:{},超时时间:{}", key, expire, timeout);

        RLock lock = redissonClient.getLock(key);
        try {
            if (lock.tryLock(expire, timeout, TimeUnit.SECONDS)) {
                log.info("枷锁成功");
                joinPoint.proceed();//继续执行下列方法
            }
        } catch (Throwable e) {
            log.error("错误信息为:{}", e.getMessage());

        } finally {
            lock.unlock();
            log.info("解锁成功");
        }


    }

}
