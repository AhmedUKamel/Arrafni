package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, String> {
}