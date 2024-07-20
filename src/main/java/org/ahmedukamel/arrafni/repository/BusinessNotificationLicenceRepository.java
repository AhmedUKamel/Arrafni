package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.BusinessNotificationLicence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessNotificationLicenceRepository
        extends JpaRepository<BusinessNotificationLicence, Long>,
        JpaSpecificationExecutor<BusinessNotificationLicence> {

    Optional<BusinessNotificationLicence> findByIdAndValidIsFalse(Long id);
}
