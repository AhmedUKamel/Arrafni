package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Page<Offer> findAllByBusiness_Owner_Id(Long id, Pageable pageable);

    Page<Offer> findAllByBusiness_Id(Long id, Pageable pageable);

    Page<Offer> findAllByBlockedAndActiveAndDeletedAndBusiness_Region_Id
            (boolean blocked, boolean active, boolean deleted, Integer regionId, Pageable pageable);

    Page<Offer> findAllByBlockedAndActiveAndDeletedAndBusiness_Id
            (boolean blocked, boolean active, boolean deleted, Long businessId, Pageable pageable);

    @Query(value = """
            SELECT  a
            FROM Offer a
            JOIN a.business.subCategories sc
            WHERE sc.id = :subCategoryId
            AND a.business.region.id = :regionId
            AND a.active = true
            AND a.deleted = false
            AND a.blocked = false
            """)
    Page<Offer> findAllBySubCategoryAndRegion(Integer subCategoryId, Integer regionId, Pageable pageable);

    @Query(value = """
            SELECT  a
            FROM Offer a
            JOIN a.business.subCategories sc
            JOIN sc.mainCategory mc
            WHERE mc.id = :mainCategoryId
            AND a.business.region.id = :regionId
            AND a.active = true
            AND a.deleted = false
            AND a.blocked = false
            """)
    Page<Offer> findAllByMainCategoryAndRegion(Integer mainCategoryId, Integer regionId, Pageable pageable);

    @Query(value = """
            SELECT a
            FROM Offer a
            WHERE a.active = true
            AND a.deleted = false
            AND a.blocked = false
            AND a.id = :id""")
    Optional<Offer> findActive(@Param(value = "id") Long id);
}