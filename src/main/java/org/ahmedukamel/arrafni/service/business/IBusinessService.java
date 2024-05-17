package org.ahmedukamel.arrafni.service.business;

public interface IBusinessService {
    Object searchBusinessesByLocation(String word, Double latitude, Double longitude, long pageSize, long pageNumber);

    Object readBusinessesBySubCategoryByLocation(Integer id, Double latitude, Double longitude, long pageSize, long pageNumber);

    Object readBusinessesByMainCategoryByLocation(Integer id, Double latitude, Double longitude, long pageSize, long pageNumber);

    Object searchBusinessesByRegionId(String word, Integer regionId, long pageSize, long pageNumber);

    Object readBusinessesBySubCategoryByRegionId(Integer id, Integer regionId, long pageSize, long pageNumber);

    Object readBusinessesByMainCategoryByRegionId(Integer id, Integer regionId, long pageSize, long pageNumber);

    Object readBusiness(Long id);

    void setFavoriteBusiness(Long id, Boolean isFavorite);

    byte[] viewBusinessLogo(String logoId);

    byte[] viewBusinessPicture(String pictureId);
}