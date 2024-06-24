package com.sunyesle.spring_boot_concurrency_control.redission;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @DistributedLock 선언시 수행되는 AOP 클래스
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAspect {
    private static final String REDISSION_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(com.sunyesle.spring_boot_concurrency_control.redission.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        // 어노테이션을 가져온다
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSION_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
        RLock rLock = redissonClient.getLock(key);

        try {
            boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (!available) {
                // 락 획득 실패
                throw new RuntimeException("LOCK_ACQUISITION_FAILED");
            }

            // DistributedLock 어노테이션이 선언된 메서드를 별도의 트랜잭션으로 실행한다
            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            // 쓰레드가 인터럽트 될 경우
            throw new RuntimeException("LOCK_INTERRUPTED_ERROR");
        } finally {
            try {
                // 락 해제
                rLock.unlock();
            } catch (IllegalMonitorStateException e) {
                // 이미 락이 해제된 경우
                log.info("Redisson Lock Already UnLock {} {}", method.getName(), key);
            }
        }
    }
}
