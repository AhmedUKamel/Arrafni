package org.ahmedukamel.arrafni.saver.region;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.region.RegionDto;
import org.ahmedukamel.arrafni.model.City;
import org.ahmedukamel.arrafni.model.Region;
import org.ahmedukamel.arrafni.model.embeddable.Location;
import org.ahmedukamel.arrafni.repository.CityRepository;
import org.ahmedukamel.arrafni.repository.RegionRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class RegionSaver implements Function<RegionDto, Region> {
    final RegionRepository regionRepository;
    final CityRepository cityRepository;

    @Override
    public Region apply(RegionDto request) {
        Location location = new Location(request.latitude(), request.longitude());
        DatabaseService.unique(regionRepository::existsByLocation, location, Region.class);
        DatabaseService.unique(regionRepository::existsByNameIgnoreCase, request.name().strip(), Region.class);
        City city = DatabaseService.get(cityRepository::findById, request.cityId(), City.class);
        Region region = new Region();
        region.setLocation(location);
        region.setName(request.name().strip());
        region.setCity(city);
        return regionRepository.save(region);
    }
}