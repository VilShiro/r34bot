package org.fbs.r34.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.fbs.r34.entity.Criticality;
import org.fbs.r34.entity.SystemLog;
import org.fbs.r34.repository.SystemLogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class ServiceExceptionCatcher {

    private final SystemLogRepository systemLogRepository;

    @Around("execution(* org.fbs.r34.service.*.*(..))")
    public Object catching(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            SystemLog systemLog = new SystemLog();
            systemLog.setMessage(e.getMessage());
            systemLog.setCriticality(Criticality.ERROR);
            systemLog.setCreatedAt(LocalDateTime.now());
            systemLog.setSignature(joinPoint.getSignature().toString());
            systemLogRepository.save(systemLog);

            throw e;
        }
    }

}
