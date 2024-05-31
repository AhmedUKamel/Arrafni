package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.arrafni.model.embeddable.PhoneNumber;
import org.ahmedukamel.arrafni.model.enumration.Gender;
import org.ahmedukamel.arrafni.model.enumration.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "USER_EMAIL_UNIQUE_CONSTRAINT", columnNames = "email"),
        @UniqueConstraint(name = "USER_PHONE_UNIQUE_CONSTRAINT", columnNames = {"country_code", "national_number"}),
        @UniqueConstraint(name = "USER_PICTURE_UNIQUE_CONSTRAINT", columnNames = "picture")
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private PhoneNumber phoneNumber;

    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String picture;

    @Column(name = "new_change")
    private String change;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender = Gender.NOT_SELECTED;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime registration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean enabled;

    @Column(nullable = false, columnDefinition = "bit(1) default true")
    private boolean accountNonLocked = true;

    @Column(name = "is_update", nullable = false, columnDefinition = "bit(1) default false")
    private boolean update;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Region region;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Business> businesses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccessToken> accessTokens = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserNotification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<AccountToken> accountTokens = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(this.role);
    }

    @Override
    public String getUsername() {
        return this.phoneNumber.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}