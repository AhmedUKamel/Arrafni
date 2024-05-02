package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.embeddable.Location;
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
            SELECT (5) AS distance, b 
            FROM Business b
            
            """)
    Map<Double, Business> selectPaginatedNearestBusinesses(Location location, long limit, long offset);
}