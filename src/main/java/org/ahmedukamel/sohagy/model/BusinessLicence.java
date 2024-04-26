package org.ahmedukamel.sohagy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity(name = "business_licences")
public class BusinessLicence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private ZonedDateTime creation;

    @Column(updatable = false)
    private ZonedDateTime expiration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean valid;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private BusinessPlan plan;

    @OneToOne
    @JoinColumn(nullable = false)
    private Payment payment;
}