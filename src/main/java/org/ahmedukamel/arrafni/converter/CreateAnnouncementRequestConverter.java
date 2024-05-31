package org.ahmedukamel.arrafni.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.arrafni.dto.announcement.CreateAnnouncementRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAnnouncementRequestConverter implements Converter<String, CreateAnnouncementRequest> {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public CreateAnnouncementRequest convert(@NonNull String source) {
        return objectMapper.readValue(source, CreateAnnouncementRequest.class);
    }
}