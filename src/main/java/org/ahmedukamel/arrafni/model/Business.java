package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.arrafni.model.embeddable.Location;
import org.ahmedukamel.arrafni.model.embeddable.PhoneNumber;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "businesses", uniqueConstraints = {@UniqueConstraint(name = "BUSINESS_LOGO_UNIQUE_CONSTRAINT", columnNames = "logo"), @UniqueConstraint(name = "BUSINESS_EMAIL_UNIQUE_CONSTRAINT", columnNames = "email"), @UniqueConstraint(name = "BUSINESS_LOCATION_UNIQUE_CONSTRAINT", columnNames = {"latitude", "longitude"})})
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long visits;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String logo;

    private String email;

    @Column(nullable = false)
    private String address;

    @Column(name = "new_change")
    private String change;

    private String series;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean enabled;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean active;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean locked;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean deleted;

    @Column(name = "is_update", nullable = false, columnDefinition = "bit(1) default false")
    private boolean update;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creation;

    private LocalDateTime expiration;

    @Embedded
    @Column(nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Region region;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "business_pictures")
    private Set<String> pictures = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "business_phone_numbers")
    private Set<PhoneNumber> numbers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "business_sub_categories")
    private Set<SubCategory> subCategories = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "business_keywords")
    private Set<Keyword> keywords = new HashSet<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<SocialLink> links = new HashSet<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BusinessLicence> licences = new HashSet<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Announcement> announcements = new HashSet<>();
}