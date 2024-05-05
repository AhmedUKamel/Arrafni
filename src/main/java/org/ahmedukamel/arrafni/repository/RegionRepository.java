package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Region;
import org.ahmedukamel.arrafni.model.embeddable.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    @Query(value = """
            SELECT r
            FROM Region r
            WHERE r.name LIKE %:word%
            """)
    List<Region> searchRegions(@Param(value = "word") String word);

    boolean existsByLocation(Location location);

    boolean existsByNameIgnoreCase(String name);
}