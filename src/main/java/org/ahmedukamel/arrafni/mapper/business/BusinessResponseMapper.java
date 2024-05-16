package org.ahmedukamel.arrafni.mapper.business;

import org.ahmedukamel.arrafni.dto.business.BusinessResponse;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.Keyword;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Component
public class BusinessResponseMapper implements Function<Business, BusinessResponse> {
    @Override
    public BusinessResponse apply(Business business) {
        Optional<User> userOptional = ContextHolderUtils.getOptionalUser();
        boolean isFavourite = userOptional.isPresent() && userOptional.get()
                .getWishlist()
                .getItems()
                .stream()
                .anyMatch(wishlistItem -> wishlistItem.getBusiness().getId().equals(business.getId()));
        return new BusinessResponse(
                business.getId(),
                business.getName(),
                business.getDescription(),
                business.getEmail(),
                business.getAddress(),
                business.getSeries(),
                business.getLocation().getLatitude(),
                business.getLocation().getLatitude(),
                StringUtils.hasLength(business.getSeries()),
                isFavourite,
                business.getNumbers().stream().map(Objects::toString).toList(),
                business.getSubCategories().stream().map(SubCategory::getId).toList(),
                business.getKeywords().stream().map(Keyword::getWord).toList(),
                business.getLinks()
        );
    }
}