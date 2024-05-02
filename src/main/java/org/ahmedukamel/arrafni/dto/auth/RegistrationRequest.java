package org.ahmedukamel.arrafni.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.ahmedukamel.arrafni.constant.RegexConstants;

public record RegistrationRequest(
        @NotBlank
        @Pattern(regexp = RegexConstants.PHONE)
        String phone,

        @NotBlank
        @Pattern(regexp = RegexConstants.PASSWORD)
        String password,

        @NotBlank
        @Pattern(regexp = RegexConstants.NAME)
        String firstName,

        @NotBlank
        @Pattern(regexp = RegexConstants.NAME)
        String lastName
) {
}