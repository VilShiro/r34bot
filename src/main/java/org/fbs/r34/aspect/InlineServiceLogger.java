package org.fbs.r34.aspect;

//import lombok.extern.log4j.Log4j2;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Log4j2
//@Component
//public class InlineServiceLogger {
//
//    @Around("execution(* org.fbs.r34.handler.InlineBotHandler(..))")
//    public Object timeLogging(ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//
//        Object result = joinPoint.proceed();
//
//        log.info("{} has been processed in {} ms", joinPoint.getSignature().getName(), System.currentTimeMillis() - start);
//
//        return result;
//    }
//
//}
