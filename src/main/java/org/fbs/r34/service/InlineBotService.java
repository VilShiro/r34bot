package org.fbs.r34.service;

import com.pengrad.telegrambot.model.request.InlineQueryResultPhoto;
import lombok.extern.log4j.Log4j2;
import org.fbs.r34.dto.PhotoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Service
@Scope(SCOPE_SINGLETON)
@Log4j2
public class InlineBotService {

    private final RestClient restClient;

    @Value("${app.r34.api_key}")
    private String apiKey;
    @Value("${app.r34.user_id}")
    private String userId;
    @Value("${app.r34.limit}")
    private int limit;

    public InlineBotService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://api.rule34.xxx").build();
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
