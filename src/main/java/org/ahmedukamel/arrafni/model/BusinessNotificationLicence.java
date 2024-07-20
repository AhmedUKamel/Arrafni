package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "business_notification_licences")
public class BusinessNotificationLicence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creation;

    @Column(updatable = false)
    private LocalDateTime expiration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean valid;

    @Column(name = "manually_activated", nullable = false, columnDefinition = "bit(1) default false")
    private boolean manual;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private BusinessNotificationPlan plan;

    @OneToOne
    @JoinColumn(updatable = false)
    private Payment payment;
}
