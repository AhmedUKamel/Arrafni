package org.ahmedukamel.arrafni.service.business;

public interface IPublicBusinessService {
    Object searchBusinesses(String word, Double latitude, Double longitude, long pageSize, long pageNumber);

    Object readBusinessesBySubCategory(Integer id, Double latitude, Double longitude, long pageSize, long pageNumber);

    Object readBusinessesByMainCategory(Integer id, Double latitude, Double longitude, long pageSize, long pageNumber);

    Object readBusiness(Long id);
}