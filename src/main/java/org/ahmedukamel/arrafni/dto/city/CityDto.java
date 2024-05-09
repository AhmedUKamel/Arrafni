package org.ahmedukamel.arrafni.dto.city;

import jakarta.validation.constraints.NotBlank;

public record CityDto(
        Integer id,

        @NotBlank
        String name
) {
}