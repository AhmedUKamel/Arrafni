package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Integer> {
    @Query(value = """
            SELECT mc
            FROM MainCategory  mc
            ORDER BY mc.id
            LIMIT :limit
            OFFSET :offset""")
    List<MainCategory> selectPaginatedMainCategories(@Param(value = "limit") int limit,
                                                     @Param(value = "offset") int offset);

    boolean existsByNameIgnoreCase(String name);
}