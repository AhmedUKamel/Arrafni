package org.ahmedukamel.arrafni.service.offer;

public interface IOfferUserService {
    Object getAllOffers(int pageSize, int pageNumber);

    Object getSubCategoryOffers(Integer subCategoryId, int pageSize, int pageNumber);

    Object getMainCategoryOffers(Integer mainCategoryId, int pageSize, int pageNumber);
}