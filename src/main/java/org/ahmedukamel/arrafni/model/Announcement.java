package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creation;

    private LocalDateTime expiration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean active;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean blocked;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean deleted;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean premium;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Business business;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL)
    private Set<AnnouncementLicence> licences = new HashSet<>();
}