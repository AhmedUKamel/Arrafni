package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.arrafni.model.enumration.NotificationType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String message;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 16, columnDefinition = "varchar(16) default 'GENERAL'")
    private NotificationType type = NotificationType.GENERAL;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserNotification> users = new HashSet<>();
}