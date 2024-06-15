package org.ahmedukamel.arrafni.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.ahmedukamel.arrafni.constant.RegexConstants;
import org.ahmedukamel.arrafni.model.enumration.Gender;

public record UpdateProfileRequest(
        @NotBlank
        @Size(min = 2, max = 32)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 32)
        String lastName,

        @NotNull
        Gender gender
) {
}