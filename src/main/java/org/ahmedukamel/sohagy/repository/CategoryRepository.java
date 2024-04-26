package org.ahmedukamel.sohagy.repository;

import org.ahmedukamel.sohagy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = """
            SELECT c
            FROM Category c
            ORDER BY c.id
            LIMIT :limit
            OFFSET :offset""")
    List<Category> selectCategories(@Param(value = "limit") Integer limit,
                                    @Param(value = "offset") Integer offset);
}