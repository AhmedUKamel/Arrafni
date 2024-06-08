package org.ahmedukamel.arrafni.service.business;

public interface IAdminBusinessService {
    void setLockStatus(Long id, boolean locked);

    void activateBusiness(Long id);

    void activateBusinessLicence(Long id);

    void approveBusinessUpdate(Long id);

    Object getPendingActivationBusinesses(int pageSize, int pageNumber);

    Object getPendingActivationBusinessLicences(int pageSize, int pageNumber);

    Object getPendingUpdateApprovalBusinesses(int pageSize, int pageNumber);

    Object getAllBusinesses(int pageSize, int pageNumber);

    Object getBusinessesByLockStatus(boolean locked, int pageSize, int pageNumber);
}