package org.ahmedukamel.arrafni.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubCategoryDtoConverter implements Converter<String, SubCategoryDto> {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public SubCategoryDto convert(@NonNull String source) {
        return objectMapper.readValue(source, SubCategoryDto.class);
    }
}
