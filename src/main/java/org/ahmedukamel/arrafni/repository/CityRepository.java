package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    @Query(value = """
            SELECT c
            FROM City c
            WHERE c.name LIKE %:word%
            """)
    List<City> searchRegions(@Param(value = "word") String word);

    boolean existsByNameIgnoreCase(String name);
}