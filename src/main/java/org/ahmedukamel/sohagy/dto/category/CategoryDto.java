package org.ahmedukamel.sohagy.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
        @NotBlank
        String name,

        @NotBlank
        String description
) {
}