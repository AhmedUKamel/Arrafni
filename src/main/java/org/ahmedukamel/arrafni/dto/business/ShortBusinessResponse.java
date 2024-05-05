package org.ahmedukamel.arrafni.dto.business;

public record ShortBusinessResponse(
        Long id,

        String name,

        String description,

        String logo,

        double distance
) {
}