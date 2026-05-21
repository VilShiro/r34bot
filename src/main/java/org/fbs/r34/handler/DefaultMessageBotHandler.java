package org.fbs.r34.handler;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import io.ksilisk.telegrambot.core.executor.TelegramBotExecutor;
import io.ksilisk.telegrambot.core.handler.update.message.MessageUpdateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultMessageBotHandler implements MessageUpdateHandler {

    private final TelegramBotExecutor telegramBotExecutor;

    @Override
    public void handle(Update update) {
        SendMessage sendMessage = new SendMessage((long) update.message().chat().id(), "Use it just like the @pic bot: type your query after the mention directly in the input field.");
        telegramBotExecutor.execute(sendMessage);
    }

}
