package org.ahmedukamel.sohagy.dto.business;

import org.ahmedukamel.sohagy.model.SocialLink;

import java.util.Collection;

public record BusinessResponse(
        String name,

        String description,

        String email,

        String address,

        String series,

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