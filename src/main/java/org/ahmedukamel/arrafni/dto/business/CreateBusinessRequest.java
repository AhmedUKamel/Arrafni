package org.ahmedukamel.arrafni.dto.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.ahmedukamel.arrafni.annotation.SingleSocialLink;
import org.ahmedukamel.arrafni.model.SocialLink;

import java.util.Collection;

public record CreateBusinessRequest(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotBlank
        String email,

        @NotBlank
        String address,

        String series,

        @NotNull
        Double latitude,

        @NotNull
        Double longitude,

        @NotNull
        @Size(min = 1, max = 5)
        Collection<String> numbers,

        @NotNull
        @Size(min = 1, max = 3)
        Collection<Integer> categories,

        @NotNull
        @Size(min = 1, max = 5)
        Collection<String> keywords,

        @NotNull
        @Size(min = 1, max = 6)
        @SingleSocialLink
        Collection<SocialLink> socialLinks
) {
}