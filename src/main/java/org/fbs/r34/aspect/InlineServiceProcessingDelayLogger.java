package org.fbs.r34.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.fbs.r34.entity.Criticality;
import org.fbs.r34.entity.SystemLog;
import org.fbs.r34.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class InlineServiceProcessingDelayLogger {

    private final SystemLogRepository systemLogRepository;

    @Value("${app.r34.rate-limit-per-s}")
    private double rateLimit;

    @Around("execution(* org.fbs.r34.handler.InlineBotHandler.*(..))")
    public Object timeLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        if (end - start > (1000 / rateLimit) * 10) {
            SystemLog systemLog = new SystemLog();
            systemLog.setMessage("Processing time to long: " + (end - start) + "ms");
            systemLog.setCriticality(Criticality.WARN);
            systemLog.setSignature(joinPoint.getSignature().toString());
            systemLog.setCreatedAt(LocalDateTime.now());
            systemLogRepository.save(systemLog);
        }

        return result;
    }

}
