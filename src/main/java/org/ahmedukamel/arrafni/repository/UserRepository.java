package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.PhoneNumber;
import org.ahmedukamel.arrafni.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(@NonNull String email);

    Optional<User> findByPhoneNumber(@NonNull PhoneNumber phoneNumber);

    boolean existsByEmailIgnoreCase(@NonNull String email);

    boolean existsByPhoneNumber(@NonNull PhoneNumber phoneNumber);
}