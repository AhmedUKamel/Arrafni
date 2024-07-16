package org.ahmedukamel.arrafni.repository;

import jakarta.transaction.Transactional;
import org.ahmedukamel.arrafni.model.Announcement;
import org.ahmedukamel.arrafni.model.embeddable.PhoneNumber;
import org.ahmedukamel.arrafni.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(@NonNull String email);

    Optional<User> findByPhoneNumber(@NonNull PhoneNumber phoneNumber);

    boolean existsByEmailIgnoreCase(@NonNull String email);

    boolean existsByPhoneNumber(@NonNull PhoneNumber phoneNumber);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE User u
            SET u.accountNonExpired = false
            WHERE u = :user
            """)
    void deleteUser(@Param(value = "user") User user);
}