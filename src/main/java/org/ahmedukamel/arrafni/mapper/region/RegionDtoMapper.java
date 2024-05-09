package org.ahmedukamel.arrafni.mapper.region;

import org.ahmedukamel.arrafni.dto.region.RegionDto;
import org.ahmedukamel.arrafni.model.Region;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RegionDtoMapper implements Function<Region, RegionDto> {
    @Override
    public RegionDto apply(Region region) {
        return new RegionDto(
                region.getId(),
                region.getLocation().getLatitude(),
                region.getLocation().getLongitude(),
                region.getName(),
                region.getCity().getId()
        );
    }
}