package org.fbs.r34.service;

import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.request.InlineQueryResultPhoto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fbs.r34.dto.PhotoDTO;
import org.fbs.r34.entity.SearchLog;
import org.fbs.r34.provider.Rule34Provider;
import org.fbs.r34.repository.SearchLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Service
@Scope(SCOPE_SINGLETON)
@Log4j2
@RequiredArgsConstructor
public class InlineBotService {

    private final SearchLogRepository searchLogRepository;
    private final Rule34Provider rule34Provider;

    @Value("${app.r34.data-limit}")
    private int limit;

    public void createSaveSearchLog(InlineQuery query) {
        SearchLog searchLog = new SearchLog();
        searchLog.setCurrentLimit(limit);
        searchLog.setQuery(query.query());
        searchLog.setUsername(query.from().username());
        searchLog.setFirstName(query.from().firstName());
        searchLog.setLastName(query.from().lastName());
        searchLog.setLangCode(query.from().languageCode());
        searchLog.setCreatedAt(LocalDateTime.now());
        searchLogRepository.save(searchLog);
    }

    public InlineQueryResultPhoto[] getReadyPhotos(InlineQuery query){
        if (!query.offset().trim().isEmpty()) {
            if (Integer.parseInt(query.offset()) % limit != 0) {
                return new InlineQueryResultPhoto[0];
            }
        }

        List<InlineQueryResultPhoto> photos = convert(
                rule34Provider.getPhotos(query)
        );

        return photos.toArray(new InlineQueryResultPhoto[0]);
    }

    private List<InlineQueryResultPhoto> convert(List<PhotoDTO> photos) {
        List<InlineQueryResultPhoto> photoList = new ArrayList<>();

        if (photos != null) {
            photoList = photos.stream()
                    .map(photo -> new InlineQueryResultPhoto(photo.getId(), photo.getFileUrl(), photo.getPreviewUrl()))
                    .toList();
        }

        return photoList;
    }
}
