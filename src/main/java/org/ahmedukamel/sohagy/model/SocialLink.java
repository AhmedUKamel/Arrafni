package org.ahmedukamel.sohagy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.sohagy.model.enumration.SocialType;

import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(value = SocialLink.class)
@Table(name = "social_links")
public class SocialLink {
    @Id
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SocialType type;

    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Business business;

    @Column(nullable = false)
    private String link;

    @Getter
    @Setter
    public static class SocialLinkId implements Serializable {
        private SocialType type;
        private Business business;
    }
}