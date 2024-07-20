package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "business_notifications")
public class BusinessNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String title;

    @Column(nullable = false, updatable = false)
    private String body;

    @Column(updatable = false)
    private String image;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Business business;

    @OneToOne(mappedBy = "businessNotification")
    private Notification notification;
}
