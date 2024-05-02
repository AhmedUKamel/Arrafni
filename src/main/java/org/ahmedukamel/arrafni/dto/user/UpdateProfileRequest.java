package org.ahmedukamel.arrafni.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.ahmedukamel.arrafni.constant.RegexConstants;
import org.ahmedukamel.arrafni.model.enumration.Gender;

public record UpdateProfileRequest(
        @NotBlank
        @Pattern(regexp = RegexConstants.NAME)
        String firstName,

        @NotBlank
        @Pattern(regexp = RegexConstants.NAME)
        String lastName,

        @NotNull
        Gender gender
) {
}