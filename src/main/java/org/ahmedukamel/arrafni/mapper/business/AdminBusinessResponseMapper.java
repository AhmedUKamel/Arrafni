package org.ahmedukamel.arrafni.mapper.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.business.AdminBusinessResponse;
import org.ahmedukamel.arrafni.dto.business.CriticalBusinessData;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.Keyword;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.ahmedukamel.arrafni.model.embeddable.PhoneNumber;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AdminBusinessResponseMapper implements Function<Business, AdminBusinessResponse> {
    private final ObjectMapper objectMapper;

    @Override
    public AdminBusinessResponse apply(Business business) {
        final CriticalBusinessData change;

        if (business.isUpdate() && !business.getChange().isBlank()) {
            try {
                change = objectMapper.readValue(business.getChange(), CriticalBusinessData.class);
            } catch (IOException e) {
                throw new RuntimeException("Unable to parse critical business data");
            }

        } else {
            change = null;
        }

        return new AdminBusinessResponse(
                business.getId(),
                business.getName(),
                business.getDescription(),
                business.getEmail(),
                business.getAddress(),
                business.getSeries(),
                // TODO:
                business.getLogo(),
                business.getLocation().getLatitude(),
                business.getLocation().getLongitude(),
                Strings.hasLength(business.getSeries()),
                Strings.hasLength(business.getEmail()),
                !business.getLinks().isEmpty(),
                business.isActive(),
                business.isLocked(),
                business.isDeleted(),
                business.isUpdate(),
                // TODO:
                business.getPictures(),
                business.getNumbers().stream().map(PhoneNumber::toString).toList(),
                business.getKeywords().stream().map(Keyword::getWord).toList(),
                business.getSubCategories().stream().map(SubCategory::getId).toList(),
                business.getLinks(),
                change
        );
    }
}