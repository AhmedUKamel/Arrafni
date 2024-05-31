package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.AnnouncementLicence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementLicenceRepository extends JpaRepository<AnnouncementLicence, Long> {
    Page<AnnouncementLicence> findAllByValid(boolean valid, Pageable pageable);
}