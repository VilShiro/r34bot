package org.fbs.r34.rule;

import com.pengrad.telegrambot.model.Message;
import io.ksilisk.telegrambot.core.handler.update.UpdateHandler;
import io.ksilisk.telegrambot.core.matcher.Matcher;
import io.ksilisk.telegrambot.core.rule.MessageUpdateRule;
import lombok.RequiredArgsConstructor;
import org.fbs.r34.handler.DefaultMessageBotHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultCommandRule implements MessageUpdateRule {

    private final DefaultMessageBotHandler handler;

    @Override
    public Matcher<Message> matcher() {
        return _ -> true;
    }

    @Override
    public UpdateHandler handler() {
        return handler;
    }
}
