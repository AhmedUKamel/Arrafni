package org.ahmedukamel.arrafni.dto.business;

import org.ahmedukamel.arrafni.model.SocialLink;

import java.util.Collection;

public record BusinessResponse(
        Long id,

        String name,

        String description,

        String email,

        String address,

        String series,

        Double latitude,

        Double longitude,

        boolean isSeries,

        boolean isFavourite,

        Collection<String> numbers,

        Collection<Integer> categories,

        Collection<String> keywords,

        Collection<SocialLink> socialLinks
) {
}