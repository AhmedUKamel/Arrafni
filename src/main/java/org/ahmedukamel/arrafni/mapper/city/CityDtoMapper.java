package org.ahmedukamel.arrafni.mapper.city;

import org.ahmedukamel.arrafni.dto.city.CityDto;
import org.ahmedukamel.arrafni.model.City;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CityDtoMapper implements Function<City, CityDto> {
    @Override
    public CityDto apply(City city) {
        return new CityDto(
                city.getId(),
                city.getName()
        );
    }
}