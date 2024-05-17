package org.ahmedukamel.arrafni.dto.business;

import org.ahmedukamel.arrafni.model.SocialLink;

import java.util.Collection;

public record OwnerBusinessResponse(
        long id,

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

        boolean active,

        boolean locked,

        boolean deleted,

        boolean update,

        Collection<String> numbers,

        Collection<Integer> categories,

        Collection<String> keywords,

        Collection<SocialLink> socialLinks
) {
}