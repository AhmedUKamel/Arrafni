package org.ahmedukamel.arrafni.service.city;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.api.ApiResponse;
import org.ahmedukamel.arrafni.dto.city.CityDto;
import org.ahmedukamel.arrafni.mapper.city.CityDtoMapper;
import org.ahmedukamel.arrafni.model.City;
import org.ahmedukamel.arrafni.repository.CityRepository;
import org.ahmedukamel.arrafni.saver.city.CitySaver;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.updater.city.CityUpdater;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService implements ICityService {
    final CityRepository repository;
    final CityDtoMapper mapper;
    final CityUpdater updater;
    final CitySaver saver;

    @Override
    public Object createCity(Object object) {
        CityDto request = (CityDto) object;
        City savedCity = saver.apply(request);
        CityDto response = mapper.apply(savedCity);
        return new ApiResponse(true, "Successful Create City.", response);
    }

    @Override
    public Object updateCity(Integer id, Object object) {
        CityDto request = (CityDto) object;
        City city = DatabaseService.get(repository::findById, id, City.class);
        City updatedCity = updater.apply(city, request);
        CityDto response = mapper.apply(updatedCity);
        return new ApiResponse(true, "Successful Update City.", response);
    }

    @Override
    public void deleteCity(Integer id) {
        City city = DatabaseService.get(repository::findById, id, City.class);
        repository.delete(city);
    }

    @Override
    public Object getCity(Integer id) {
        City city = DatabaseService.get(repository::findById, id, City.class);
        CityDto response = mapper.apply(city);
        return new ApiResponse(true, "Successful Get City.", response);
    }

    @Override
    public Object getCities() {
        List<CityDto> response = repository
                .findAll()
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Get Cities.", response);
    }

    @Override
    public Object searchCities(String word) {
        List<CityDto> response = repository
                .searchRegions(word.strip())
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Successful Search Cities.", response);
    }
}
