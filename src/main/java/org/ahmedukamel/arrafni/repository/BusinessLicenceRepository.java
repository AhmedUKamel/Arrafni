package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.BusinessLicence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessLicenceRepository extends JpaRepository<BusinessLicence, Long> {
    Page<BusinessLicence> findAllByValidIsFalse(Pageable pageable);

    void deleteAllByBusinessAndValidIsFalse(Business business);
}