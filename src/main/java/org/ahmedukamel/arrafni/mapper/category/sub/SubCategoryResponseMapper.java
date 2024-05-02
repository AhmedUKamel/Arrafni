package org.ahmedukamel.arrafni.mapper.category.sub;

import org.ahmedukamel.arrafni.dto.category.sub.SubCategoryResponse;
import org.ahmedukamel.arrafni.model.SubCategory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component

public class SubCategoryResponseMapper implements Function<SubCategory, SubCategoryResponse> {
    @Override
    public SubCategoryResponse apply(SubCategory subCategory) {
        return new SubCategoryResponse(
                subCategory.getId(),
                subCategory.getMainCategory().getId(),
                subCategory.getName(),
                subCategory.getDescription(),
                subCategory.getBusinesses().size()
        );
    }
}