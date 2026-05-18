package org.fbs.r34.provider;

import com.pengrad.telegrambot.model.InlineQuery;
import org.fbs.r34.dto.PhotoDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public interface Rule34Provider {

    List<PhotoDTO> getPhotos(InlineQuery query);

}
