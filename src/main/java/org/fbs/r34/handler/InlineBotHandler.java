package org.fbs.r34.handler;

import com.pengrad.telegrambot.model.Update;
import io.ksilisk.telegrambot.core.executor.TelegramBotExecutor;
import io.ksilisk.telegrambot.core.handler.update.inline.InlineUpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fbs.r34.service.InlineBotService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
@Log4j2
@RequiredArgsConstructor
public class InlineBotHandler implements InlineUpdateHandler {

    private final TelegramBotExecutor telegramBotExecutor;
    private final InlineBotService inlineBotService;

    @Override
    public void handle(Update update) {
        update.inlineQuery()
    }

}
