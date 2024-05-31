package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Page<Announcement> findAllByBusiness_Owner_Id(Long id, Pageable pageable);

    Page<Announcement> findAllByBusiness_Id(Long id, Pageable pageable);

    Page<Announcement> findAllByBlockedAndActiveAndDeletedAndPremiumAndBusiness_Region_Id
            (boolean blocked, boolean active, boolean deleted, boolean premium, Integer regionId, Pageable pageable);

    Page<Announcement> findAllByBlockedAndActiveAndDeletedAndBusiness_Region_Id
            (boolean blocked, boolean active, boolean deleted, Integer regionId, Pageable pageable);

    Page<Announcement> findAllByBlockedAndActiveAndDeletedAndBusiness_Id
            (boolean blocked, boolean active, boolean deleted, Long businessId, Pageable pageable);

    @Query(value = """
            SELECT a
            FROM Announcement a
            WHERE a.active = true
            AND a.deleted = false
            AND a.blocked = false
            AND a.id = :id""")
    Optional<Announcement> findActive(@Param(value = "id") Long id);
}