package org.fbs.r34.provider;

import com.google.common.util.concurrent.RateLimiter;
import com.pengrad.telegrambot.model.InlineQuery;
import org.fbs.r34.dto.PhotoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.concurrent.*;

@Component
public class AsyncRateLimitedRule34Provider implements Rule34Provider {

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final RateLimiter rateLimiter;
    private final RestClient restClient;

    @Value("${app.r34.api-key}")
    private String apiKey;
    @Value("${app.r34.user-id}")
    private String userId;
    @Value("${app.r34.data-limit}")
    private int limit;

    public AsyncRateLimitedRule34Provider(
            @Value("${app.r34.rate-limit-per-s}") double rateLimit,
            RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://api.rule34.xxx").build();
        rateLimiter = RateLimiter.create(rateLimit);
    }

    @Override
    public List<PhotoDTO> getPhotos(InlineQuery query) {
        try {
            return loadPhotos(() -> {
                rateLimiter.acquire(1);

                String tags = query.query();
                int offsetInt = 0;
                try {
                    offsetInt = Integer.parseInt(query.offset());
                } catch (NumberFormatException _) {}
                int pid = offsetInt/limit;

                return restClient.get().uri(
                        "/index.php?page=dapi&s=post&q=index&json=1&tags="
                                +tags.trim().toLowerCase().replace(' ', '+')
                                +"&pid="+pid+"&limit="+limit+"&api_key="+apiKey+"&user_id="+userId)
                        .retrieve()
                        .body(new ParameterizedTypeReference<>() {});
            });
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PhotoDTO> loadPhotos(Callable<List<PhotoDTO>> callable) throws InterruptedException, ExecutionException {
        Future<List<PhotoDTO>> future = executorService.submit(callable);
        return future.get();
    }

}
