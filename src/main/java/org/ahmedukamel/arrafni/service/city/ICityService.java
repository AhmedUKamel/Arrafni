package org.ahmedukamel.arrafni.service.city;

public interface ICityService {
    Object createCity(Object object);

    Object updateCity(Integer id, Object object);

    void deleteCity(Integer id);

    Object getCity(Integer id);

    Object getCities();

    Object searchCities(String word);
}