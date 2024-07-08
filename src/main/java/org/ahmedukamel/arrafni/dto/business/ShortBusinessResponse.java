package org.ahmedukamel.arrafni.dto.business;

public record ShortBusinessResponse(
        Long id,

        long visits,

        String name,

        String address,

        String description,

        String logo,

        double distance
) {
}