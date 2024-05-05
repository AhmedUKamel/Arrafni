package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    boolean existsByEmailIgnoreCase(String email);

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
    List<Object[]> searchNearestBusinessWithPagination(
            @Param("word") String word,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
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
            AND b.deleted = false
            AND b.locked = false
            AND sc.id = :subCategoryId
            ORDER BY distance ASC
            LIMIT :limit
            OFFSET :offset
            """)
    List<Object[]> selectNearestBusinessBySubCategoryWithPagination(
            @Param("subCategoryId") Integer subCategoryId,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
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
            AND b.deleted = false
            AND b.locked = false
            AND mc.id = :mainCategoryId
            ORDER BY distance ASC
            LIMIT :limit
            OFFSET :offset
            """)
    List<Object[]> selectNearestBusinessByMainCategoryWithPagination(
            @Param("mainCategoryId") Integer mainCategoryId,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("limit") long limit,
            @Param("offset") long offset
    );
}