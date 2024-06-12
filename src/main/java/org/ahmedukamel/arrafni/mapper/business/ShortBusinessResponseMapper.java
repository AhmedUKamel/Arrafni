package org.ahmedukamel.arrafni.mapper.business;

import org.ahmedukamel.arrafni.dto.business.ShortBusinessResponse;
import org.ahmedukamel.arrafni.model.Business;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ShortBusinessResponseMapper extends BusinessPictureMapper
        implements Function<Object[], ShortBusinessResponse> {
    @Override
    public ShortBusinessResponse apply(Object[] objects) {
        Business business = (Business) objects[0];
        double distance = (double) objects[1];
        return new ShortBusinessResponse(
                business.getId(),
                business.getVisits(),
                business.getName(),
                business.getDescription(),
                super.mapLogo(business.getLogo()),
                distance
        );
    }
}