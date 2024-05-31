package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Page<Announcement> findAllByBusiness_Owner_Id(Long id, Pageable pageable);

    Page<Announcement> findAllByBusiness_Id(Long id, Pageable pageable);
}