package org.ahmedukamel.arrafni.dto.category.sub;

public record SubCategoryResponse(
        Integer id,
        Integer mainCategoryId,
        String name,
        String description,
        int businessCount
) {
}