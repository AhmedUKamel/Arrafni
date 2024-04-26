package org.ahmedukamel.sohagy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "announcement_licences")
public class AnnouncementLicence {
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
    private Announcement announcement;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private AnnouncementPlan plan;

    @OneToOne
    @JoinColumn(updatable = false)
    private Payment payment;
}