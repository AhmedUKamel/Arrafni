package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(value = UserNotification.UserNotificationId.class)
@Table(name = "user_notifications")
public class UserNotification {
    @Id
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Notification notification;

    @Column(name = "is_read", nullable = false, columnDefinition = "bit(1) default false")
    private boolean read;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserNotificationId implements Serializable {
        private User user;
        private Notification notification;
    }
}