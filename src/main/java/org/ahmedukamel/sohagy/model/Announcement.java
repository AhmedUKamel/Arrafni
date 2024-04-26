package org.ahmedukamel.sohagy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String title;

    @Column(nullable = false, updatable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false)
    private String poster;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime creation;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime expiration;

    @Column(nullable = false, columnDefinition = "bit(1) default true")
    private boolean active = true;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean blocked;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Business business;

    @OneToMany(mappedBy = "announcement")
    private Set<AnnouncementLicence> licences = new HashSet<>();
}