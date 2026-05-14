package org.fbs.r34.handler;

import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineQueryResultPhoto;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import io.ksilisk.telegrambot.core.executor.TelegramBotExecutor;
import io.ksilisk.telegrambot.core.handler.update.inline.InlineUpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fbs.r34.service.InlineBotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
@Log4j2
@RequiredArgsConstructor
public class InlineBotHandler implements InlineUpdateHandler {

    private final TelegramBotExecutor botExecutor;
    private final InlineBotService service;

    @Value("${app.r34.limit}")
    private int limit;

    @Override
    public void handle(Update update) {

        InlineQuery query = update.inlineQuery();
        InlineQueryResultPhoto[] photos = service.getReadyPhotos(
                query.offset(),
                query.query()
        );

        int nextOffset = Math.min(limit, photos.length);

        if (!query.offset().isEmpty()) {
            nextOffset += Integer.parseInt(query.offset());
        }

        AnswerInlineQuery answer = new AnswerInlineQuery(
                query.id(),
                photos
        )
                .nextOffset("" + nextOffset)
                .cacheTime(10)
                .isPersonal(true);

        botExecutor.execute(answer);
    }

}
