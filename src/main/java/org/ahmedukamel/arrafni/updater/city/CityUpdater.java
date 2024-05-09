package org.ahmedukamel.arrafni.updater.city;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.city.CityDto;
import org.ahmedukamel.arrafni.model.City;
import org.ahmedukamel.arrafni.repository.CityRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CityUpdater implements BiFunction<City, CityDto, City> {
    final CityRepository repository;

    @Override
    public City apply(City city, CityDto request) {
        String name = request.name().strip();
        if (!city.getName().equalsIgnoreCase(name)) {
            DatabaseService.unique(repository::existsByNameIgnoreCase, name, City.class);
        }
        city.setName(name);
        return repository.save(city);
    }
}