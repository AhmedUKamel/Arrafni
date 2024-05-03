package org.ahmedukamel.arrafni.dto.category.main;

import jakarta.validation.constraints.NotBlank;

public record MainCategoryDto(
        @NotBlank
        String name,

        @NotBlank
        String logo
) {
}