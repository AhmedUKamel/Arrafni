package org.ahmedukamel.sohagy.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.sohagy.dto.business.CreateBusinessRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateBusinessRequestConverter implements Converter<String, CreateBusinessRequest> {
    final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public CreateBusinessRequest convert(@NonNull String source) {
        return mapper.readValue(source, CreateBusinessRequest.class);
    }
}