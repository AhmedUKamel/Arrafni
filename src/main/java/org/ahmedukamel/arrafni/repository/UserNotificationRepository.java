package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotification.UserNotificationId> {
}