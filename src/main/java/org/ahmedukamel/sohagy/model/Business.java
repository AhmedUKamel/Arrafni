package org.ahmedukamel.sohagy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.sohagy.model.embeddable.Location;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "businesses", uniqueConstraints = {
        @UniqueConstraint(name = "BUSINESS_NAME_UNIQUE_CONSTRAINT", columnNames = "name"),
        @UniqueConstraint(name = "BUSINESS_LOGO_UNIQUE_CONSTRAINT", columnNames = "logo"),
        @UniqueConstraint(name = "BUSINESS_EMAIL_UNIQUE_CONSTRAINT", columnNames = "email"),
        @UniqueConstraint(name = "BUSINESS_LOCATION_UNIQUE_CONSTRAINT", columnNames = "location")
})
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String logo;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(name = "new_change")
    private String change;

    private String series;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean active;

    @Column(nullable = false, columnDefinition = "bit(1) default true")
    private boolean locked = true;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean deleted;

    @Column(name = "is_update", nullable = false, columnDefinition = "bit(1) default false")
    private boolean update;

    @Embedded
    @Column(nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ElementCollection
    @CollectionTable(name = "business_pictures")
    private Set<String> pictures = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "business_phone_numbers")
    private Set<PhoneNumber> numbers = new HashSet<>();

    @ManyToMany(mappedBy = "businesses")
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(mappedBy = "businesses")
    private Set<Keyword> keywords = new HashSet<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<SocialLink> links = new HashSet<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BusinessLicence> licences = new HashSet<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Announcement> announcements = new HashSet<>();
}