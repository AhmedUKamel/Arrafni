package org.ahmedukamel.arrafni.dto.business;

import org.ahmedukamel.arrafni.model.SocialLink;

import java.util.Collection;

public record AdminBusinessResponse(
        Long id,

        String name,

        String description,

        String email,

        String address,

        String series,

        String logo,

        Double latitude,

        Double longitude,

        boolean isSeries,

        boolean isEmail,

        boolean isContainsSocialLinks,

        boolean active,

        boolean locked,

        boolean deleted,

        boolean update,

        Collection<String> pictures,

        Collection<String> numbers,

        Collection<String> keywords,

        Collection<Integer> categories,

        Collection<SocialLink> socialLinks,

        CriticalBusinessData change
) {
}