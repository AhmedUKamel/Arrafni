package org.ahmedukamel.arrafni.dto.region;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegionDto(
        Integer id,

        @NotNull
        Double latitude,

        @NotNull
        Double longitude,

        @NotBlank
        String name,

        @NotNull
        Integer cityId
) {
}