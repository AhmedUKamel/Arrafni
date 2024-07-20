package org.ahmedukamel.arrafni.repository;

import org.ahmedukamel.arrafni.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
