package org.ahmedukamel.arrafni.mapper.category.main;

import org.ahmedukamel.arrafni.dto.category.main.MainCategoryResponse;
import org.ahmedukamel.arrafni.model.MainCategory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MainCategoryResponseMapper implements Function<MainCategory, MainCategoryResponse> {
    @Override
    public MainCategoryResponse apply(MainCategory mainCategory) {
        return new MainCategoryResponse(
                mainCategory.getId(),
                mainCategory.getName(),
                mainCategory.getDescription(),
                mainCategory.getSubCategories().size()
        );
    }
}