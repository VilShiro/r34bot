package org.fbs.r34.service;

import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.request.InlineQueryResultPhoto;
import lombok.extern.log4j.Log4j2;
import org.fbs.r34.dto.PhotoDTO;
import org.fbs.r34.entity.Criticality;
import org.fbs.r34.entity.SearchLog;
import org.fbs.r34.entity.SystemLog;
import org.fbs.r34.repository.SearchLogRepository;
import org.fbs.r34.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Service
@Scope(SCOPE_SINGLETON)
@Log4j2
public class InlineBotService {

    private final RestClient restClient;
    private final SearchLogRepository searchLogRepository;
    private final SystemLogRepository systemLogRepository;

    @Value("${app.r34.api_key}")
    private String apiKey;
    @Value("${app.r34.user_id}")
    private String userId;
    @Value("${app.r34.limit}")
    private int limit;

    public InlineBotService(RestClient.Builder restClientBuilder,
                            SearchLogRepository searchLogRepository,
                            SystemLogRepository systemLogRepository) {
        this.restClient = restClientBuilder.baseUrl("https://api.rule34.xxx").build();
        this.searchLogRepository = searchLogRepository;
        this.systemLogRepository = systemLogRepository;
    }

    public void createSaveSearchLog(InlineQuery query) {
        try {
            SearchLog searchLog = new SearchLog();
            searchLog.setCurrentLimit(limit);
            searchLog.setQuery(query.query());
            searchLog.setUsername(query.from().username());
            searchLog.setFirstName(query.from().firstName());
            searchLog.setLastName(query.from().lastName());
            searchLog.setLangCode(query.from().languageCode());
            searchLog.setCreatedAt(LocalDateTime.now());
            searchLogRepository.save(searchLog);
        } catch (Exception e) {
            SystemLog systemLog = new SystemLog();
            systemLog.setCreatedAt(LocalDateTime.now());
            systemLog.setCriticality(Criticality.ERROR);
            systemLog.setMessage(e.getMessage());
        }
    }

    public InlineQueryResultPhoto[] getReadyPhotos(String offset, String query) {
        int offsetInt = 0;
        try {
            offsetInt = Integer.parseInt(offset);
        } catch (NumberFormatException _) {}

        List<InlineQueryResultPhoto> photos = convert(requestPhotos(query, offsetInt/limit));
        log.info("Request for new photos by tags = {}, has been processed, been found {} photos", query, photos.size());

        return photos.toArray(new InlineQueryResultPhoto[0]);
    }

    private List<InlineQueryResultPhoto> convert(List<PhotoDTO> photos) {
        List<InlineQueryResultPhoto> photoList = new ArrayList<>();

        if (!(photos == null)) {
            photoList = photos.stream()
                    .map(photo -> new InlineQueryResultPhoto(photo.getId(), photo.getFileUrl(), photo.getPreviewUrl()))
                    .toList();
        }

        return photoList;
    }

    private List<PhotoDTO> requestPhotos(String tags, int pid) {
        List<PhotoDTO> photos = restClient.get().uri(
                    "/index.php?page=dapi&s=post&q=index&json=1&tags="
                        +tags.trim().toLowerCase().replace(' ', '+')
                        +"&pid="+pid+"&limit="+limit+"&api_key="+apiKey+"&user_id="+userId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        assert photos != null;

        return photos;
    }
}
