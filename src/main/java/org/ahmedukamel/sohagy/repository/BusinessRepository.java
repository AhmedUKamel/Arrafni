package org.ahmedukamel.sohagy.repository;

import org.ahmedukamel.sohagy.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}