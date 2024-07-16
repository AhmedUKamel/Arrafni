package org.ahmedukamel.arrafni.repository;

import jakarta.transaction.Transactional;
import org.ahmedukamel.arrafni.model.Announcement;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.Region;
import org.ahmedukamel.arrafni.model.embeddable.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    boolean existsByEmailIgnoreCase(String email);

//    boolean existsByLocation(Location location);

    @Query(value = """
            SELECT b
            FROM Business b
            WHERE b.id = :id
            AND b.deleted = false""")
    Optional<Business> findNonDeletedById(@Param(value = "id") Long id);

    @Query(value = """
            SELECT b
            FROM Business b
            WHERE b.id = :id
            AND b.deleted = false
            AND b.active = true
            AND b.enabled = true
            AND b.locked = false""")
    Optional<Business> findVisibleById(@Param(value = "id") Long id);

    @Query(value = """
            SELECT b, (
                6371 * ACOS (
                    SIN (RADIANS (:latitude)) *
                    SIN (RADIANS (b.location.latitude)) +
                    COS (RADIANS (:latitude)) *
                    COS (RADIANS (b.location.latitude)) *
                    COS (
                        RADIANS (b.location.longitude) -
                        RADIANS(:longitude)
                    )
                )
            ) AS distance
            FROM Business b
            LEFT JOIN b.keywords k
            WHERE b.active = true
            AND b.enabled = true
            AND b.deleted = false
            AND b.locked = false
            AND (
                b.name LIKE %:word%
                OR b.series LIKE %:word%
                OR k.word LIKE %:word%
            )
            ORDER BY distance ASC
            LIMIT :limit
            OFFSET :offset
            """)
    List<Object[]> searchNearestBusinessByLocationWithPagination(
            @Param("word") String word,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("limit") long limit,
            @Param("offset") long offset
    );

    @Query(value = """
            SELECT b
            FROM Business b
            LEFT JOIN b.keywords k
            WHERE b.active = true
            AND b.enabled = true
            AND b.deleted = false
            AND b.locked = false
            AND b.region = :region
            AND (
                b.name LIKE %:word%
                OR b.series LIKE %:word%
                OR k.word LIKE %:word%
            )
            ORDER BY b.id ASC
            LIMIT :limit
            OFFSET :offset
            """)
    List<Business> searchNearestBusinessByRegionWithPagination(
            @Param("word") String word,
            @Param("region") Region region,
            @Param("limit") long limit,
            @Param("offset") long offset
    );

    @Query(value = """
            SELECT b, (
                6371 * ACOS (
                    SIN (RADIANS (:latitude)) *
                    SIN (RADIANS (b.location.latitude)) +
                    COS (RADIANS (:latitude)) *
                    COS (RADIANS (b.location.latitude)) *
                    COS (
                        RADIANS (b.location.longitude) -
                        RADIANS(:longitude)
                    )
                )
            ) AS distance
            FROM Business b
            JOIN b.subCategories sc
            WHERE b.active = true
            AND b.enabled = true
            AND b.deleted = false
            AND b.locked = false
            AND sc.id = :subCategoryId
            ORDER BY distance ASC
            LIMIT :limit
            OFFSET :offset
            """)
    List<Object[]> selectNearestBusinessBySubCategoryAndLocationWithPagination(
            @Param("subCategoryId") Integer subCategoryId,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("limit") long limit,
            @Param("offset") long offset
    );

    @Query(value = """
            SELECT b
            FROM Business b
            JOIN b.subCategories sc
            WHERE b.active = true
            AND b.enabled = true
            AND b.deleted = false
            AND b.locked = false
            AND b.region = :region
            AND sc.id = :subCategoryId
            ORDER BY b.id ASC
            LIMIT :limit
            OFFSET :offset
            """)
    List<Business> selectNearestBusinessBySubCategoryAndRegionWithPagination(
            @Param("subCategoryId") Integer subCategoryId,
            @Param("region") Region region,
            @Param("limit") long limit,
            @Param("offset") long offset
    );

    @Query(value = """
            SELECT b, (
                6371 * ACOS (
                    SIN (RADIANS (:latitude)) *
                    SIN (RADIANS (b.location.latitude)) +
                    COS (RADIANS (:latitude)) *
                    COS (RADIANS (b.location.latitude)) *
                    COS (
                        RADIANS (b.location.longitude) -
                        RADIANS(:longitude)
                    )
                )
            ) AS distance
            FROM Business b
            JOIN b.subCategories sc
            JOIN MainCategory mc ON mc.id = sc.mainCategory.id
            WHERE b.active = true
            AND b.enabled = true
            AND b.deleted = false
            AND b.locked = false
            AND mc.id = :mainCategoryId
            ORDER BY distance ASC
            LIMIT :limit
            OFFSET :offset
            """)
    List<Object[]> selectNearestBusinessByMainCategoryAndLocationWithPagination(
            @Param("mainCategoryId") Integer mainCategoryId,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("limit") long limit,
            @Param("offset") long offset
    );

    @Query(value = """
            SELECT b
            FROM Business b
            JOIN b.subCategories sc
            JOIN MainCategory mc ON mc.id = sc.mainCategory.id
            WHERE b.active = true
            AND b.enabled = true
            AND b.deleted = false
            AND b.locked = false
            AND b.region = :region
            AND mc.id = :mainCategoryId
            ORDER BY b.id ASC
            LIMIT :limit
            OFFSET :offset
            """)
    List<Business> selectNearestBusinessByMainCategoryAndRegionWithPagination(
            @Param("mainCategoryId") Integer mainCategoryId,
            @Param("region") Region region,
            @Param("limit") long limit,
            @Param("offset") long offset
    );

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE Business b
            SET b.visits = b.visits + 1
            WHERE b.id = :businessId
            """)
    void incrementVisits(@Param("businessId") Long businessId);

    Page<Business> findAllByOwner_Id(Long ownerId, Pageable pageable);

    Page<Business> findAllByEnabledIsFalseAndDeletedIsFalse(Pageable pageable);

    Page<Business> findAllByUpdateIsTrue(Pageable pageable);

    Page<Business> findAllByLocked(boolean locked, Pageable pageable);

    Page<Business> findAllByRegionAndLockedIsFalseAndActiveIsTrueAndDeletedIsFalseAndEnabledIsTrue(Region region, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Business b " +
           "SET b.active = false " +
           "WHERE b.expiration IS NOT NULL " +
           "AND b.expiration < CURRENT_TIMESTAMP " +
           "AND b.active = true")
    void deactivateExpiredBusinesses();

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE Business b
            SET b.deleted = true
            WHERE b = :business
            """)
    void deleteBusiness(@Param(value = "business") Business business);
}