package org.ahmedukamel.sohagy.mapper.category;

import org.ahmedukamel.sohagy.dto.category.CategoryResponse;
import org.ahmedukamel.sohagy.model.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component

public class CategoryResponseMapper implements Function<Category, CategoryResponse> {
    @Override
    public CategoryResponse apply(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getBusinesses().size()
        );
    }
}