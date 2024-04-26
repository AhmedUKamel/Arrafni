package org.ahmedukamel.sohagy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.sohagy.model.enumration.Gender;
import org.ahmedukamel.sohagy.model.enumration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "USER_EMAIL_UNIQUE_CONSTRAINT", columnNames = "email"),
        @UniqueConstraint(name = "USER_PHONE_UNIQUE_CONSTRAINT", columnNames = "phoneNumber"),
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

    @Enumerated(value = EnumType.STRING)
    @Column(length = 16, columnDefinition = "varchar(16) default 'USER'")
    private Role role = Role.USER;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 16, columnDefinition = "varchar(16) default 'NOT_SELECTED'")
    private Gender gender = Gender.NOT_SELECTED;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime registration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean enabled;

    @Column(nullable = false, columnDefinition = "bit(1) default true")
    private boolean accountNonLocked = true;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Wishlist wishlist;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Business> businesses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccessToken> tokens = new HashSet<>();

    public void setEmail(String email) {
        this.email = email.strip().toLowerCase();
    }

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