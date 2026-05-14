package org.fbs.r34.handler;

import com.pengrad.telegrambot.model.Update;
import io.ksilisk.telegrambot.core.handler.update.command.CommandUpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
@Log4j2
@RequiredArgsConstructor
public class CommandBotHandler implements CommandUpdateHandler {

    @Override
    public void handle(Update update) {

    }

    @Override
    public Set<String> commands() {
        return Set.of();
    }

}
