package org.ahmedukamel.arrafni.service.region;

public interface IRegionService {
    Object createRegion(Object object);

    Object updateRegion(Integer id, Object object);

    void deleteRegion(Integer id);

    Object getRegion(Integer id);

    Object getRegions();

    Object searchRegions(String word);
}