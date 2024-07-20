package org.ahmedukamel.arrafni.mapper.business;

import org.ahmedukamel.arrafni.dto.business.OwnerBusinessResponse;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.ahmedukamel.arrafni.model.Keyword;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Function;

@Component
public class OwnerBusinessResponseMapper extends BusinessPictureMapper
        implements Function<Business, OwnerBusinessResponse> {
    @Override
    public OwnerBusinessResponse apply(Business business) {
        return new OwnerBusinessResponse(
                business.getId(),
                business.getVisits(),
                business.getName(),
                business.getDescription(),
                business.getEmail(),
                business.getAddress(),
                business.getSeries(),
                super.mapLogo(business.getLogo()),
                super.mapPictures(business.getPictures()),
                business.getLocation().getLatitude(),
                business.getLocation().getLongitude(),
                StringUtils.hasLength(business.getSeries()),
                business.isActive(),
                business.isLocked(),
                business.isDeleted(),
                business.isUpdate(),
                business.getNotificationCount(),
                business.getNumbers().stream().map(Objects::toString).toList(),
                business.getSubCategories().stream().map(SubCategory::getId).toList(),
                business.getKeywords().stream().map(Keyword::getWord).toList(),
                business.getLinks()
        );
    }
}