package org.ahmedukamel.arrafni.saver.city;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.city.CityDto;
import org.ahmedukamel.arrafni.model.City;
import org.ahmedukamel.arrafni.repository.CityRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CitySaver implements Function<CityDto, City> {
    final CityRepository repository;

    @Override
    public City apply(CityDto request) {
        String name = request.name().strip();
        DatabaseService.unique(repository::existsByNameIgnoreCase, name, City.class);
        City city = new City();
        city.setName(name);
        return repository.save(city);
    }
}