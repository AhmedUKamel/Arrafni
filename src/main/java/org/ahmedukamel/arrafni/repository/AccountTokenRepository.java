package org.ahmedukamel.arrafni.repository;

import jakarta.transaction.Transactional;
import org.ahmedukamel.arrafni.model.AccountToken;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.model.enumration.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTokenRepository extends JpaRepository<AccountToken, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE AccountToken t " +
                   "SET t.revoked = true " +
                   "WHERE t.user = :user " +
                   "AND t.type = :tokenType")
    void revokeTokens(@Param(value = "user") User user,
                      @Param(value = "tokenType") TokenType tokenType);
}