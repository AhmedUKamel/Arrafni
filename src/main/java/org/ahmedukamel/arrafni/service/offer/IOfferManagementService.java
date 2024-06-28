package org.ahmedukamel.arrafni.service.offer;

import org.springframework.web.multipart.MultipartFile;

public interface IOfferManagementService {
    Object createOffer(Object object, MultipartFile poster);

    void deleteOffer(Long id);

    Object renewOffer(Object object);

    Object getOfferLicences(Long id);

    Object getOffer(Long id);

    Object getOffers(int pageSize, int pageNumber);

    Object getBusinessOffers(Long id, int pageSize, int pageNumber);
}