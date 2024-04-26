package org.ahmedukamel.sohagy.dto.category;

public record CategoryResponse(
        Integer id,
        String name,
        String description,
        int businessCount
) {
}