package org.ahmedukamel.arrafni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ahmedukamel.arrafni.model.enumration.SocialType;

import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(value = SocialLink.class)
@Table(name = "business_social_links")
public class SocialLink {
    @Id
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SocialType type;

    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(nullable = false, updatable = false)
    private Business business;

    @Column(nullable = false)
    private String link;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialLinkId implements Serializable {
        private SocialType type;
        private Business business;
    }
}