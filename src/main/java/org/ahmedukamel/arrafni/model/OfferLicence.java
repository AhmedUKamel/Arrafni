package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "offer_licences")
public class OfferLicence {
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
    private Offer offer;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private OfferPlan plan;

    @OneToOne
    @JoinColumn(updatable = false)
    private Payment payment;
}