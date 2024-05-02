package org.ahmedukamel.arrafni.dto.category.main;

public record MainCategoryResponse(
        Integer id,
        String name,
        String description,
        int subCategoryCount
) {
}