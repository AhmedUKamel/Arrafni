package org.ahmedukamel.arrafni.mapper.business;

import org.ahmedukamel.arrafni.constant.ApiConstants;

import java.util.Collection;

public abstract class BusinessPictureMapper {
    public final String mapLogo(String logoId) {
        return "%s/api/v1/business/public/logo?id=%s"
                .formatted(ApiConstants.BASE_URL, logoId);
    }

    public final String mapPicture(String pictureId) {
        return "%s/api/v1/business/public/picture?id=%s"
                .formatted(ApiConstants.BASE_URL, pictureId);
    }

    public final Collection<String> mapPictures(Collection<String> pictureIds) {
        return pictureIds.stream().map(this::mapPicture).toList();
    }
}