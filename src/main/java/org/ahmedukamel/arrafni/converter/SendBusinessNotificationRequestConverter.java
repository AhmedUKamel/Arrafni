package org.ahmedukamel.arrafni.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.arrafni.dto.business.SendBusinessNotificationRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendBusinessNotificationRequestConverter
        implements Converter<String, SendBusinessNotificationRequest> {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public SendBusinessNotificationRequest convert(@NonNull String source) {
        return objectMapper.readValue(source, SendBusinessNotificationRequest.class);
    }
}
