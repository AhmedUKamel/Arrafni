package org.ahmedukamel.arrafni.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.arrafni.dto.offer.CreateOfferRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOfferRequestConverter implements Converter<String, CreateOfferRequest> {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public CreateOfferRequest convert(@NonNull String source) {
        return objectMapper.readValue(source, CreateOfferRequest.class);
    }
}