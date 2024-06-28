package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.OfferLicence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferLicenceRepository extends JpaRepository<OfferLicence, Long> {
    Page<OfferLicence> findAllByValid(boolean valid, Pageable pageable);
}