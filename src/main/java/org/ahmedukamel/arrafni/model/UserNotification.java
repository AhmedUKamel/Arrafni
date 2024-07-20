package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_notifications")
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Notification notification;

    @Column(name = "is_read", nullable = false, columnDefinition = "bit(1) default false")
    private boolean read;
}