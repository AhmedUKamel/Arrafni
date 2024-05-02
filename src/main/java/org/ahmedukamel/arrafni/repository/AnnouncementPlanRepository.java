package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.AnnouncementPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementPlanRepository extends JpaRepository<AnnouncementPlan, Integer> {
    @Query(value = """
            SELECT p
            FROM AnnouncementPlan p
            ORDER BY p.id
            LIMIT :limit
            OFFSET :offset""")
    List<AnnouncementPlan> selectPaginatedAnnouncementPlan(@Param(value = "limit") long limit,
                                                           @Param(value = "offset") long offset);

    @Query(value = """
            SELECT p
            FROM AnnouncementPlan p
            WHERE p.active = true
            AND p.id = :id
            """)
    Optional<AnnouncementPlan> findActiveById(@Param(value = "id") Integer id);

    boolean existsByName(String name);
}