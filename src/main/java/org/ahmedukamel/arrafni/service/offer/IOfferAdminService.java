package org.ahmedukamel.arrafni.service.offer;

public interface IOfferAdminService {
    Object getPendingOfferLicences(int pageSize, int pageNumber);

    Object activateOfferLicence(Long id);

    Object setOfferBlockStatus(Long id, Boolean blocked);
}