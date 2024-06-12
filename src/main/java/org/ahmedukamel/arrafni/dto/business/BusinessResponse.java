package org.ahmedukamel.arrafni.dto.business;

import org.ahmedukamel.arrafni.model.SocialLink;

import java.util.Collection;

public record BusinessResponse(
        Long id,

        long visits,

        String name,

        String description,

        String email,

        String address,

        String series,

        String logo,

        Collection<String> pictures,

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