package org.ahmedukamel.arrafni.dto.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.ahmedukamel.arrafni.annotation.SingleSocialLink;
import org.ahmedukamel.arrafni.model.SocialLink;

import java.util.Collection;

public record UpdateBusinessRequest(
        @NotBlank
        String description,

        @NotNull
        @Size(min = 1, max = 5)
        Collection<String> numbers,

        @Size(min = 1, max = 6)
        @SingleSocialLink
        Collection<SocialLink> socialLinks,

        @NotBlank
        String name,

        @NotBlank
        String address,

        @NotNull
        Double latitude,

        @NotNull
        Double longitude
) {
}