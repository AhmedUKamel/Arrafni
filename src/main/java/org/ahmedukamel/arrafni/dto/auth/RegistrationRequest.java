package org.ahmedukamel.arrafni.dto.auth;

import jakarta.validation.constraints.*;
import org.ahmedukamel.arrafni.constant.RegexConstants;

public record RegistrationRequest(
        @NotBlank
        @Pattern(regexp = RegexConstants.PHONE)
        String phone,

        @NotBlank
        @Pattern(regexp = RegexConstants.PASSWORD)
        String password,

        @NotBlank
        @Size(min = 2, max = 32)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 32)
        String lastName,

        @NotNull
        @Min(value = 1)
        Integer regionId
) {
}