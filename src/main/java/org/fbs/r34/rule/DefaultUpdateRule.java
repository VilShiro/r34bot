package org.fbs.r34.rule;

import com.pengrad.telegrambot.model.InlineQuery;
import io.ksilisk.telegrambot.core.handler.update.UpdateHandler;
import io.ksilisk.telegrambot.core.matcher.Matcher;
import io.ksilisk.telegrambot.core.rule.InlineUpdateRule;
import io.ksilisk.telegrambot.core.rule.UpdateRule;
import org.fbs.r34.handler.InlineBotHandler;
import org.springframework.stereotype.Component;

@Component
public class DefaultUpdateRule implements InlineUpdateRule {
    private final InlineBotHandler handler;

    public DefaultUpdateRule(InlineBotHandler handler) {
        this.handler = handler;
    }

    @Override
    public Matcher<InlineQuery> matcher() {
        return _ -> true;
    }

    @Override
    public UpdateHandler handler() {
        return handler;
    }
}