package org.ahmedukamel.sohagy.mapper.business;

import org.ahmedukamel.sohagy.dto.business.BusinessResponse;
import org.ahmedukamel.sohagy.model.Business;
import org.ahmedukamel.sohagy.model.Category;
import org.ahmedukamel.sohagy.model.Keyword;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Function;

@Component
public class BusinessResponseMapper implements Function<Business, BusinessResponse> {
    @Override
    public BusinessResponse apply(Business business) {
        return new BusinessResponse(
                business.getName(),
                business.getDescription(),
                business.getEmail(),
                business.getAddress(),
                business.getSeries(),
                business.getLocation().getLatitude(),
                business.getLocation().getLongitude(),
                StringUtils.hasLength(business.getSeries()),
                business.isActive(),
                business.isLocked(),
                business.isDeleted(),
                business.isUpdate(),
                business.getNumbers().stream().map(Objects::toString).toList(),
                business.getCategories().stream().map(Category::getId).toList(),
                business.getKeywords().stream().map(Keyword::getWord).toList(),
                business.getLinks()
        );
    }
}