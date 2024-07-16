package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.AccessToken;
import org.ahmedukamel.arrafni.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {
    void deleteAllByUser(User user);
}