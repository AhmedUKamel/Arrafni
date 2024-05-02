package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.BusinessPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessPlanRepository extends JpaRepository<BusinessPlan, Integer> {
    @Query(value = """
            SELECT p
            FROM BusinessPlan p
            ORDER BY p.id
            LIMIT :limit
            OFFSET :offset""")
    List<BusinessPlan> selectPlans(@Param(value = "limit") Integer limit,
                                   @Param(value = "offset") Integer offset);

    @Query(value = """
            SELECT p
            FROM BusinessPlan p
            WHERE p.id = :id
            AND p.active = true""")
    Optional<BusinessPlan> findActiveById(@Param(value = "id") Integer id);

    boolean existsByName(String name);
}