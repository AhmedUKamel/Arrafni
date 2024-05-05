package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    @Query(value = """
            SELECT c
            FROM SubCategory c
            ORDER BY c.id
            LIMIT :limit
            OFFSET :offset""")
    List<SubCategory> selectPaginatedSubCategories(@Param(value = "limit") int limit,
                                                   @Param(value = "offset") int offset);

    boolean existsByNameIgnoreCase(String name);
}