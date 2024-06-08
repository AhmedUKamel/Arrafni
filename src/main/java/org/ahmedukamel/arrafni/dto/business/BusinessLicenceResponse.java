package org.ahmedukamel.arrafni.dto.business;

public record BusinessLicenceResponse(
        Long businessId,

        Long licenceId,

        String name,

        String description,

        String address
) {
}