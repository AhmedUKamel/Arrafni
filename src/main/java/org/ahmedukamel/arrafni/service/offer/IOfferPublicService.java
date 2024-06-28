package org.ahmedukamel.arrafni.service.offer;

public interface IOfferPublicService {
    Object getAllOffers(Integer regionId, int pageSize, int pageNumber);

    Object getSubCategoryOffers(Integer regionId, Integer subCategoryId, int pageSize, int pageNumber);

    Object getMainCategoryOffers(Integer regionId, Integer mainCategoryId, int pageSize, int pageNumber);

    Object getBusinessOffers(Long id, int pageSize, int pageNumber);

    Object getOfferById(Long id);

    byte[] getOfferPosters(Long id);
}