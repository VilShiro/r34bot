package org.fbs.r34.aspect;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.fbs.r34.entity.Criticality;
import org.fbs.r34.entity.SystemLog;
import org.fbs.r34.repository.SystemLogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class UpdateNoMatchStrategyLogCatcher {

    private final SystemLogRepository systemLogRepository;

    @Before("execution(* io.ksilisk.telegrambot.core.strategy.impl.LoggingUpdateNoMatchStrategy.handle(..)) && args(update)")
    public void catching(Update update) {
        SystemLog systemLog = new SystemLog();
        systemLog.setMessage(update.toString());
        systemLog.setCriticality(Criticality.WARN);
        systemLog.setCreatedAt(LocalDateTime.now());
        systemLog.setSignature("io.ksilisk.telegrambot.core.strategy.impl.LoggingUpdateNoMatchStrategy.handle(Update)");
        systemLogRepository.save(systemLog);
    }

}
