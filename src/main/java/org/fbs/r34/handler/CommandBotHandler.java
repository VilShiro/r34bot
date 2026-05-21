package org.fbs.r34.handler;

import com.google.common.io.Files;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import io.ksilisk.telegrambot.core.executor.TelegramBotExecutor;
import io.ksilisk.telegrambot.core.handler.update.command.CommandUpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fbs.r34.exception.AuthorizationException;
import org.fbs.r34.repository.SearchLogRepository;
import org.fbs.r34.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
@Log4j2
@RequiredArgsConstructor
public class CommandBotHandler implements CommandUpdateHandler {

    private final SearchLogRepository searchLogRepository;
    private final SystemLogRepository systemLogRepository;
    private final TelegramBotExecutor botExecutor;

    @Value("${app.telegram.root.user-id}")
    private long rootId;

    @Override
    public void handle(Update update) throws AuthorizationException {
        if (update.message().from().id() != rootId) throw new AuthorizationException(update.message().from());

        String[] args = update.message().text().split(" ");
        StringBuilder sb = new  StringBuilder();

        switch (args[0]) {

            case "/sendSearchLogs" -> searchLogRepository.findAll(
                    PageRequest.of(0, 200)
            ).forEach(object -> sb.append(object).append("\n"));

            case "/sendSystemLogs" -> systemLogRepository.findAll(
                    PageRequest.of(0, 200)
            ).forEach(object -> sb.append(object).append("\n"));

            default -> {
                return;
            }
        }

        String fileName = "temp" + System.currentTimeMillis() + ".txt";

        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                Files.asCharSink(file, StandardCharsets.UTF_8).write(sb.toString());

                SendDocument sendDocument = new SendDocument((long) update.message().chat().id(), file);
                botExecutor.execute(sendDocument);

                file.delete();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Set<String> commands() {
        return Set.of(
                "/sendSearchLogs",
                "/sendSystemLogs"
        );
    }
}
