package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
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
            SELECT (
               6371 * ACOS (
                    COS (RADIANS (:latitude)) *\s
                    COS (RADIANS (b.location.latitude)) *\s
                    COS (RADIANS (b.location.latitude) -\s
                            RADIANS (:longitude)) +\s
                    SIN (RADIANS (:latitude)) *\s
                    SIN (RADIANS (b.location.longitude))
               )
            ) AS distance, b
            FROM Business b
            WHERE b.active = true
            ORDER BY (
               6371 * ACOS (
                    COS (RADIANS (:latitude)) *\s
                    COS (RADIANS (b.location.latitude)) *\s
                    COS (RADIANS (b.location.latitude) -\s
                            RADIANS (:longitude)) +\s
                    SIN (RADIANS (:latitude)) *\s
                    SIN (RADIANS (b.location.longitude))
               )
            )
            LIMIT :limit
            OFFSET :offset
            """)
    Map<Double, Business> selectPaginatedNearestBusinesses(@Param(value = "latitude") double latitude,
                                                           @Param(value = "longitude") double longitude,
                                                           @Param(value = "limit") long limit,
                                                           @Param(value = "offset") long offset);
}