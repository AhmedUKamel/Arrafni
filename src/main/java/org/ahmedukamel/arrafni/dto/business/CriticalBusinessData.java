package org.ahmedukamel.arrafni.dto.business;

import java.util.Collection;

public record CriticalBusinessData(
        String address,

        String series,

        Double latitude,

        Double longitude,

        Collection<Integer> categories
) {
}