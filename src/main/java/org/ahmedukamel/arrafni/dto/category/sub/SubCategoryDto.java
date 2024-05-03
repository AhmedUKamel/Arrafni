package org.ahmedukamel.arrafni.dto.category.sub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubCategoryDto(
        @NotNull
        Integer mainCategoryId,

        @NotBlank
        String name,

        @NotBlank
        String logo
) {
}