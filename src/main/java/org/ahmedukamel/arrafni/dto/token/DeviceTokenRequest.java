package org.ahmedukamel.arrafni.dto.token;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

public record DeviceTokenRequest(
        @NotBlank
        String token,

        @NonNull
        @Min(value = 1)
        Integer regionId
) {
}
